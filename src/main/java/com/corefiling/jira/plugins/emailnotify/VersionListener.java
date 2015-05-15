package com.corefiling.jira.plugins.emailnotify;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.project.VersionCreateEvent;
import com.atlassian.jira.event.project.VersionDeleteEvent;
import com.atlassian.jira.event.project.VersionUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Simple JIRA listener using the atlassian-event library.
 * Created by pwc on 15/05/15.
 */
public class VersionListener implements InitializingBean, DisposableBean {
  private static final Logger LOG = LoggerFactory.getLogger("atlassian.plugin");
  private final EventPublisher _eventPublisher;

  /**
   * Constructor.
   * @param eventPublisher injected {@code EventPublisher} implementation.
   */
  public VersionListener(final EventPublisher eventPublisher) {
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
   * Receives any {@code VersionUpdatedEvent}s sent by JIRA.
   * @param versionEvent the VersionUpdatedEvent passed to us
   */
  @EventListener
  public void onVersionUpdatedEvent(final VersionUpdatedEvent versionEvent) {
    final String versionName = versionEvent.getVersion().getName();
    LOG.info("UPDATE event for version {}", versionName);
    EmailQueuer.queueEmail("pwc@corefiling.com", "test", "UPDATE event for version" + versionName);
  }

  /**
   * Receives any {@code VersionCreateEvent}s sent by JIRA.
   * @param versionEvent the VersionCreateEvent passed to us
   */
  @EventListener
  public void onVersionCreateEvent(final VersionCreateEvent versionEvent) {
    final String versionName = versionEvent.getVersion().getName();
    LOG.info("CREATE event for version {}", versionName);
    EmailQueuer.queueEmail("pwc@corefiling.com", "test", "CREATE event for version" + versionName);
  }

  /**
   * Receives any {@code VersionDeleteEvent}s sent by JIRA.
   * @param versionEvent the VersionDeleteEvent passed to us
   */
  @EventListener
  public void onVersionDeleteEvent(final VersionDeleteEvent versionEvent) {
    final String versionName = versionEvent.getVersion().getName();
    LOG.info("DELETE event for version {}", versionName);
    EmailQueuer.queueEmail("pwc@corefiling.com", "test", "DELETE event for version" + versionName);
  }
}
