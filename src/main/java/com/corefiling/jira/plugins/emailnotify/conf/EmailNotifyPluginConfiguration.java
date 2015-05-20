package com.corefiling.jira.plugins.emailnotify.conf;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.corefiling.jira.plugins.emailnotify.components.issue.conf.IssueSettings;
import com.corefiling.jira.plugins.emailnotify.components.version.conf.VersionSettings;

/**
 * Created by pwc on 20/05/15.
 */
public class EmailNotifyPluginConfiguration {
  public static final String SETTINGS_KEY = "CoreFilingEmailNotify";
  public static final String BOOLEAN_TRUE = "__emailnotify_bool_y";
  public static final String BOOLEAN_FALSE = "__emailnotify_bool_n";

  private final PluginSettings _settings;
  private final VersionSettings _versionSettings;
  private final IssueSettings _issueSettings;

  public EmailNotifyPluginConfiguration(final PluginSettingsFactory pluginSettingsFactory) {
    _settings = pluginSettingsFactory.createSettingsForKey(SETTINGS_KEY);
    _versionSettings = new VersionSettings(_settings);
    _issueSettings = new IssueSettings(_settings);
  }

  public VersionSettings getVersionSettings() {
    return _versionSettings;
  }

  public IssueSettings getIssueSettings() {
    return _issueSettings;
  }
}
