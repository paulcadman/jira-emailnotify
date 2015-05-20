package com.corefiling.jira.plugins.emailnotify.components.version;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.project.VersionCreateEvent;
import com.atlassian.jira.event.project.VersionDeleteEvent;
import com.atlassian.jira.event.project.VersionUpdatedEvent;
import com.corefiling.jira.plugins.emailnotify.components.version.conf.VersionSettings;
import com.corefiling.jira.plugins.emailnotify.conf.EmailNotifyPluginConfiguration;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Simple JIRA listener using the atlassian-event library.
 * Created by pwc on 15/05/15.
 */
public class VersionListener implements InitializingBean, DisposableBean {
  private final EventPublisher _eventPublisher;
  private final VersionSettings _pluginSettings;

  /**
   * Constructor.
   * @param eventPublisher injected {@code EventPublisher} implementation.
   */
  public VersionListener(final EventPublisher eventPublisher, final EmailNotifyPluginConfiguration pluginSettings) {
    _eventPublisher = eventPublisher;
    _pluginSettings = pluginSettings.getVersionSettings();
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
    if (_pluginSettings.isEnabled()) {
      new VersionEmail(versionEvent, _pluginSettings.getRecipientsList()).send();
    }
  }

  /**
   * Receives any {@code VersionCreateEvent}s sent by JIRA.
   * @param versionEvent the VersionCreateEvent passed to us
   */
  @EventListener
  public void onVersionCreateEvent(final VersionCreateEvent versionEvent) {
    if (_pluginSettings.isEnabled()) {
      new VersionEmail(versionEvent, _pluginSettings.getRecipientsList()).send();
    }
  }

  /**
   * Receives any {@code VersionDeleteEvent}s sent by JIRA.
   * @param versionEvent the VersionDeleteEvent passed to us
   */
  @EventListener
  public void onVersionDeleteEvent(final VersionDeleteEvent versionEvent) {
    if (_pluginSettings.isEnabled()) {
      new VersionEmail(versionEvent, _pluginSettings.getRecipientsList()).send();
    }
  }
}
