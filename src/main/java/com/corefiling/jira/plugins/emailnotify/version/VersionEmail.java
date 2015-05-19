package com.corefiling.jira.plugins.emailnotify.version;

import com.atlassian.jira.event.project.VersionCreateEvent;
import com.atlassian.jira.event.project.VersionDeleteEvent;
import com.atlassian.jira.event.project.VersionUpdatedEvent;
import com.corefiling.jira.plugins.emailnotify.EmailQueuer;
import com.corefiling.jira.plugins.emailnotify.version.content.VersionEmailContent;
import com.corefiling.jira.plugins.emailnotify.version.content.VersionCreateEmailContent;
import com.corefiling.jira.plugins.emailnotify.version.content.VersionDeleteEmailContent;
import com.corefiling.jira.plugins.emailnotify.version.content.VersionUpdateEmailContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Created by pwc on 19/05/15.
 */
public class VersionEmail {
  private VersionEmailContent _delegate;
  private Collection<String> _emails;
  private final Logger LOG = LoggerFactory.getLogger("atlassian.plugin");

  public VersionEmail(final VersionCreateEvent event, final Collection<String> emails) {
    _emails = emails;
    _delegate = new VersionCreateEmailContent(event);
  }

  public VersionEmail(final VersionDeleteEvent event, final Collection<String> emails) {
    _emails = emails;
    _delegate = new VersionDeleteEmailContent(event);
  }

  public VersionEmail(final VersionUpdatedEvent event, final Collection<String> emails) {
    _emails = emails;
    _delegate = new VersionUpdateEmailContent(event);
  }

  public void send() {
    for (String email : _emails) {
      LOG.debug("sending email to: " + email);
      LOG.debug("message: " + _delegate.getMessage());
      LOG.debug("subject: " + _delegate.getSubject());
      LOG.debug("header: " + _delegate.getHeader());
      EmailQueuer.queueEmail(email, _delegate.getSubject(), _delegate.getMessage(), _delegate.getHeader());
    }
  }
}
