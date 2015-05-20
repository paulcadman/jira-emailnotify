package com.corefiling.jira.plugins.emailnotify.components.issue;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.event.type.EventType;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.Issue;

import com.corefiling.jira.plugins.emailnotify.conf.EmailNotifyPluginConfiguration;
import com.corefiling.jira.plugins.emailnotify.components.issue.conf.IssueSettings;
import com.corefiling.jira.plugins.emailnotify.email.EmailContent;
import com.corefiling.jira.plugins.emailnotify.components.issue.content.IssueTriageInSprintEmailContent;
import com.corefiling.jira.plugins.emailnotify.util.EmailQueuer;
import com.google.common.base.Optional;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Simple JIRA listener using the atlassian-event library.
 */
public class IssueListener implements InitializingBean, DisposableBean {
  private static final Logger LOG = LoggerFactory.getLogger("atlassian.plugin");
  private final EventPublisher _eventPublisher;
  private final CustomFieldManager _customFieldManger;
  private final IssueSettings _pluginSettings;

  /**
   * Constructor.
   * @param customFieldManager injected {@code CustomFieldManager} implementation.
   * @param eventPublisher injected {@code EventPublisher} implementation.
   */
  public IssueListener(final CustomFieldManager customFieldManager, final EventPublisher eventPublisher, final EmailNotifyPluginConfiguration pluginSettings) {
    _customFieldManger = customFieldManager;
    _eventPublisher = eventPublisher;
    _pluginSettings = pluginSettings.getIssueSettings();
  }

  /**
   * Called when the plugin has been enabled.
   * @throws Exception
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    // register ourselves with the EventPublisher
    _eventPublisher.register(this);
  }

  /**
   * Called when the plugin is being disabled or removed.
   * @throws Exception
   */
  @Override
  public void destroy() throws Exception {
    // unregister ourselves with the EventPublisher
    _eventPublisher.unregister(this);
  }

  private boolean isAddedToSprint(final IssueEvent issueEvent) {
    Optional<String> sprintChange = IssueChanges.getFieldChange(issueEvent, "Sprint");
    return sprintChange.isPresent() && !sprintChange.get().equals("");
  }

  private boolean evaluateCondition(final IssueEvent event) {
    Binding binding = new Binding();
    GroovyShell shell = new GroovyShell(binding);
    binding.setVariable("issue", event.getIssue());
    binding.setVariable("changes", IssueChanges.getAllChanges(event));

    Object value = shell.evaluate(_pluginSettings.getCondition());
    if (!(value instanceof Boolean)) {
      LOG.warn("emailnotify: Expected groovyCondition to return boolean");
      return false;
    }
    else {
      return (Boolean) value;
    }
  }

  /**
   * Receives any {@code IssueEvent}s sent by JIRA.
   * @param issueEvent the IssueEvent passed to us
   */
  @EventListener
  public void onIssueEvent(final IssueEvent issueEvent) {
    final Long eventTypeId = issueEvent.getEventTypeId();
    final Issue issue = issueEvent.getIssue();
    final Optional<String> sprintChange = IssueChanges.getFieldChange(issueEvent, "Sprint");

    if (_pluginSettings.isEnabled()
            && eventTypeId.equals(EventType.ISSUE_UPDATED_ID)
            && issue.getStatusObject().getName().equals("Triage")
            && (sprintChange.isPresent() && !sprintChange.get().equals(""))) {
      EmailContent emailContent = new IssueTriageInSprintEmailContent(issueEvent, sprintChange.get());
      EmailQueuer.queueEmail(issueEvent.getUser().getEmailAddress(), emailContent);
      for (String address : _pluginSettings.getRecipientsList()) {
        EmailQueuer.queueEmail(address, emailContent);
      }
    }
  }
}
