package com.corefiling.jira.plugins.emailnotify;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.google.common.collect.Lists;

import java.util.Collection;

/**
 * Created by pwc on 15/05/15.
 */
public class EmailNotifyPluginConfiguration {
  public static final String SETTINGS_KEY = "CorefilingEmailNotify";
  public static final String VERSION_CHANGES_KEY = "versionChanges";
  public static final String VERSION_EMAILS_KEY = "versionEmails";

  private static final String BOOLEAN_TRUE = "__emailnotify_bool_y";

  private static final String BOOLEAN_FALSE = "__emailnotigy_bool_n";

  private final PluginSettings _settings;

  public EmailNotifyPluginConfiguration(final PluginSettingsFactory pluginSettingsFactory) {
    _settings = pluginSettingsFactory.createSettingsForKey(SETTINGS_KEY);
  }

  public boolean getNotifyVersionChanges() {
    Object out = _settings.get(VERSION_CHANGES_KEY);

    return out == null || out.equals(BOOLEAN_TRUE);
  }

  public void setNotifyVersionChanges(final boolean setting) {
    _settings.put(VERSION_CHANGES_KEY, setting ? BOOLEAN_TRUE : BOOLEAN_FALSE);
  }

  public void setNotifyVersionEmails(final String emails) {
    _settings.put(VERSION_EMAILS_KEY, emails);
  }

  public String getNotifyVersionEmails() {
    Object emails = _settings.get(VERSION_EMAILS_KEY);
    if (emails != null) {
      return (String) emails;
    }
    else {
      return "";
    }
  }

  public Collection<String> getNotifyVersionEmailsList() {
    final Collection<String> out = Lists.newArrayList();
    for (String email : getNotifyVersionEmails().split(",")) {
      out.add(email.trim());
    }
    return out;
  }
}
