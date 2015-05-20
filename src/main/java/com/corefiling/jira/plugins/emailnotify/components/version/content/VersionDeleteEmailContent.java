package com.corefiling.jira.plugins.emailnotify.components.version.content;

import com.atlassian.jira.event.project.VersionDeleteEvent;

/**
 * Created by pwc on 19/05/15.
 */
public class VersionDeleteEmailContent extends AbstractVersionEmailContent {
  public VersionDeleteEmailContent(final VersionDeleteEvent event) {
    super(event);
  }

  protected String getEventType() {
    return "DELETED";
  }
}
