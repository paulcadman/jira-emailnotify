package com.corefiling.jira.plugins.emailnotify.version.content;

import com.atlassian.jira.event.project.VersionCreateEvent;

/**
 * Created by pwc on 19/05/15.
 */
public class VersionCreateEmailContent extends AbstractVersionEmailContent {
  public VersionCreateEmailContent(final VersionCreateEvent event) {
    super(event);
  }

  protected String getEventType() {
    return "CREATED";
  }
}
