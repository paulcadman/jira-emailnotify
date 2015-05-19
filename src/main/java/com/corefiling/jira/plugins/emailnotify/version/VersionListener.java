package com.corefiling.jira.plugins.emailnotify.version;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.properties.APKeys;
import com.atlassian.jira.event.project.AbstractVersionEvent;
import com.atlassian.jira.event.project.VersionCreateEvent;
import com.atlassian.jira.event.project.VersionDeleteEvent;
import com.atlassian.jira.event.project.VersionUpdatedEvent;
import com.corefiling.jira.plugins.emailnotify.EmailNotifyPluginConfiguration;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Simple JIRA listener using the atlassian-event library.
 * Created by pwc on 15/05/15.
 */
public class VersionListener implements InitializingBean, DisposableBean {
  private final EventPublisher _eventPublisher;
  private final EmailNotifyPluginConfiguration _pluginSettings;

  /**
   * Constructor.
   * @param eventPublisher injected {@code EventPublisher} implementation.
   */
  public VersionListener(final EventPublisher eventPublisher, final EmailNotifyPluginConfiguration pluginSettings) {
    _eventPublisher = eventPublisher;
    _pluginSettings = pluginSettings;
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
    if (_pluginSettings.getNotifyVersionChanges()) {
      new VersionEmail(versionEvent, _pluginSettings.getNotifyVersionEmailsList()).send();
    }
  }

  /**
   * Receives any {@code VersionCreateEvent}s sent by JIRA.
   * @param versionEvent the VersionCreateEvent passed to us
   */
  @EventListener
  public void onVersionCreateEvent(final VersionCreateEvent versionEvent) {
    if (_pluginSettings.getNotifyVersionChanges()) {
      new VersionEmail(versionEvent, _pluginSettings.getNotifyVersionEmailsList()).send();
    }
  }

  /**
   * Receives any {@code VersionDeleteEvent}s sent by JIRA.
   * @param versionEvent the VersionDeleteEvent passed to us
   */
  @EventListener
  public void onVersionDeleteEvent(final VersionDeleteEvent versionEvent) {
    if (_pluginSettings.getNotifyVersionChanges()) {
      new VersionEmail(versionEvent, _pluginSettings.getNotifyVersionEmailsList()).send();
    }
  }
}
