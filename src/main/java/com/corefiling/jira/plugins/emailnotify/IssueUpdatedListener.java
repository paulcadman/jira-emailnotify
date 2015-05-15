package com.corefiling.jira.plugins.emailnotify;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.event.type.EventType;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.Issue;
import java.util.List;
import org.ofbiz.core.entity.GenericValue;
import org.ofbiz.core.entity.GenericEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Simple JIRA listener using the atlassian-event library.
 */
public class IssueUpdatedListener implements InitializingBean, DisposableBean {
  private static final Logger LOG = LoggerFactory.getLogger("atlassian.plugin");
  private final EventPublisher _eventPublisher;
  private final CustomFieldManager _customFieldManger;

  /**
   * Constructor.
   * @param _customFieldManager injected {@code CustomFieldManager} implementation.
   * @param _eventPublisher injected {@code EventPublisher} implementation.
   */
  public IssueUpdatedListener(final CustomFieldManager customFieldManager, final EventPublisher eventPublisher) {
    _customFieldManger = customFieldManager;
    _eventPublisher = eventPublisher;
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

  /**
   * Receives any {@code IssueEvent}s sent by JIRA.
   * @param issueEvent the IssueEvent passed to us
   */
  @EventListener
  public void onIssueEvent(final IssueEvent issueEvent) {
    final Long eventTypeId = issueEvent.getEventTypeId();
    final Issue issue = issueEvent.getIssue();
    // if it's an event we're interested in, log it
    if (eventTypeId.equals(EventType.ISSUE_CREATED_ID)) {
      LOG.info("Issue {} has been created at {}.", issue.getKey(), issue.getCreated());
    }
    else if (eventTypeId.equals(EventType.ISSUE_RESOLVED_ID)) {
      LOG.info("Issue {} has been resolved at {}.", issue.getKey(), issue.getResolutionDate());
    }
    else if (eventTypeId.equals(EventType.ISSUE_CLOSED_ID)) {
      LOG.info("Issue {} has been closed at {}.", issue.getKey(), issue.getUpdated());
    }
    else if (eventTypeId.equals(EventType.ISSUE_UPDATED_ID)) {
      try {
        new IssueChanges(issueEvent).isFieldChanged(null, null);
      }
      catch (GenericEntityException e) {
        LOG.warn(e.toString());
      }
    }
  }
}
