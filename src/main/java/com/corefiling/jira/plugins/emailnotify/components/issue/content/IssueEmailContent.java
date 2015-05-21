package com.corefiling.jira.plugins.emailnotify.components.issue.content;

import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.issue.Issue;
import com.corefiling.jira.plugins.emailnotify.email.AbstractEmailContent;

/**
 * Created by pwc on 19/05/15.
 */
public class IssueEmailContent extends AbstractEmailContent {
  private final IssueEvent _issueEvent;
  private final String _sprintName;

  public IssueEmailContent(final IssueEvent issueEvent, final String sprintName) {
    _issueEvent = issueEvent;
    _sprintName = sprintName;
  }

  private Issue getIssue() {
    return _issueEvent.getIssue();
  }

  private String getIssueUrl() {
    return String.format("%s/browse/%s", getBaseUrl(), getIssue().getKey());
  }

  @Override
  public String getSubject() {
    return String.format("Triage issue %s has been added to a sprint", getIssue().getKey());
  }

  @Override
  public String getBody() {
    return String.format("Issue %s has been added to a sprint (%s) but is in the Triage state.\nPlease visit %s to correct this.", getIssue().getKey(), _sprintName, getIssueUrl());
  }

  @Override
  public String getHeader() {
    return "triage issue in a sprint";
  }
}
