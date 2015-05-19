package com.corefiling.jira.plugins.emailnotify.version.content;

import com.atlassian.jira.event.project.VersionUpdatedEvent;

/**
 * Created by pwc on 19/05/15.
 */
public class VersionUpdateEmailContent extends AbstractVersionEmailContent {
  public VersionUpdateEmailContent(final VersionUpdatedEvent event) {
    super(event);
  }

  public String getEventType() {
    return "UPDATED";
  }
}
