package com.corefiling.jira.plugins.emailnotify;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.project.VersionUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Simple JIRA listener using the atlassian-event library.
 * Created by pwc on 15/05/15.
 */
public class VersionUpdatedListener implements InitializingBean, DisposableBean {
  private static final Logger LOG = LoggerFactory.getLogger("atlassian.plugin");
  private final EventPublisher _eventPublisher;

  /**
   * Constructor.
   * @param eventPublisher injected {@code EventPublisher} implementation.
   */
  public VersionUpdatedListener(final EventPublisher eventPublisher) {
    _eventPublisher = eventPublisher;
  }

  /**
   * Called when the plugin is enabled
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
   * Receives any {@code VersionUpdatedEvents}s sent by JIRA.
   * @param versionEvent the IssueEvent passed to us
   */
  @EventListener
  public void onIssueEvent(final VersionUpdatedEvent versionEvent) {
    LOG.info("UPDATE event for version {}", versionEvent.getVersion().getName());
  }
}
