package com.corefiling.jira.plugins.emailnotify.version.content;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.properties.APKeys;
import com.atlassian.jira.event.project.AbstractVersionEvent;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.version.Version;

/**
 * Created by pwc on 19/05/15.
 */
abstract class AbstractVersionEmailContent implements VersionEmailContent {
  private final AbstractVersionEvent _event;

  public AbstractVersionEmailContent(final AbstractVersionEvent event) {
    _event = event;
  }

  protected abstract String getEventType();

  private Version getVersion() {
    return _event.getVersion();
  }

  private Project getProject() {
    return getVersion().getProjectObject();
  }

  public String getSubject() {
    return String.format("[JIRA] %s", getMessageStart());
  }

  protected String getMessageStart() {
    return String.format("Version %s in project %s has been %s.", getVersion().getName(), getProject().getKey(), getEventType());
  }

  protected String getBaseUrl() {
    return ComponentAccessor.getApplicationProperties().getString(APKeys.JIRA_BASEURL);
  }

  protected String getProjectVersionUrl() {
    return String.format("%s/plugins/servlet/project-config/%s/versions", getBaseUrl(), getProject().getKey());
  }

  protected String getMessageEnd() {
    return String.format("Visit %s for more details.", getProjectVersionUrl());
  }

  public String getMessage() {
    return String.format("%s\n%s", getMessageStart(), getMessageEnd());
  }
}
