package com.corefiling.jira.plugins.emailnotify.components.version.conf;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.corefiling.jira.plugins.emailnotify.conf.EmailNotifyPluginConfiguration;
import com.google.common.collect.Lists;

import java.util.Collection;

/**
 * Created by pwc on 20/05/15.
 */
public class VersionSettings {
  private static final String VERSION_ENABLED_KEY = "versionChanges";
  private static final String VERSION_EMAILS_KEY = "versionEmails";

  private final PluginSettings _settings;

  public VersionSettings(final PluginSettings settings) {
    _settings = settings;
  }

  public boolean isEnabled() {
    Object out = _settings.get(VERSION_ENABLED_KEY);
    return out == null || out.equals(EmailNotifyPluginConfiguration.BOOLEAN_TRUE);
  }

  public void setEnabled(final boolean enabled) {
    _settings.put(VERSION_ENABLED_KEY, enabled ? EmailNotifyPluginConfiguration.BOOLEAN_TRUE : EmailNotifyPluginConfiguration.BOOLEAN_FALSE);
  }

  public String getRecipients() {
    Object emails = _settings.get(VERSION_EMAILS_KEY);
    if (emails != null) {
      return (String) emails;
    }
    else {
      return "";
    }
  }

  public void setRecipients(final String emails) {
    _settings.put(VERSION_EMAILS_KEY, emails);
  }

  public Collection<String> getRecipientsList() {
    final Collection<String> out = Lists.newArrayList();
    for (String email : getRecipients().split(",")) {
      out.add(email.trim());
    }
    return out;
  }
}
