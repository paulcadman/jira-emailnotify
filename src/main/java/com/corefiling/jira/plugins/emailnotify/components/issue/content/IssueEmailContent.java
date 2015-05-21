package com.corefiling.jira.plugins.emailnotify.components.issue.content;

import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.issue.Issue;
import com.corefiling.jira.plugins.emailnotify.change.Change;
import com.corefiling.jira.plugins.emailnotify.components.issue.IssueChanges;
import com.corefiling.jira.plugins.emailnotify.email.AbstractEmailContent;
import groovy.lang.Binding;
import groovy.lang.GString;
import groovy.lang.GroovyShell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

/**
 * Created by pwc on 19/05/15.
 */
public class IssueEmailContent extends AbstractEmailContent {
  private final IssueEvent _issueEvent;
  private final String _message;
  private final String _subject;

  private static final Logger LOG = LoggerFactory.getLogger("atlassian.plugin");

  public IssueEmailContent(final IssueEvent issueEvent, final String subject, final String message) {
    _issueEvent = issueEvent;
    _message = message;
    _subject = subject;
  }

  private Issue getIssue() {
    return _issueEvent.getIssue();
  }

  private Map<String, Change> getAllChanges() {
    return IssueChanges.getAllChanges(_issueEvent);
  }

  private String getIssueUrl() {
    return String.format("%s/browse/%s", getBaseUrl(), getIssue().getKey());
  }

  private String evaluateGstring(final String string) {
    Binding binding = new Binding();
    GroovyShell shell = new GroovyShell(binding);
    binding.setVariable("issue", getIssue());
    binding.setVariable("changes", getAllChanges());
    binding.setVariable("issueUrl", getIssueUrl());

    Object value = shell.evaluate(String.format("\"\"\"%s\"\"\"", string));
    if (!(value instanceof GString)) {
      LOG.warn("emailnotify: Expected groovyMessage to return GString");
      return "";
    }
    else {
      return ((GString) value).toString();
    }
  }

  @Override
  public String getSubject() {
    return evaluateGstring(_subject);
  }

  @Override
  public String getBody() {
    return evaluateGstring(_message);
  }

  @Override
  public String getHeader() {
    return "issueUpdate";
  }
}
