package com.corefiling.jira.plugins.emailnotify.components.issue.conf;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.corefiling.jira.plugins.emailnotify.conf.EmailNotifyPluginConfiguration;
import com.google.common.collect.Lists;

import java.util.Collection;

/**
 * Created by pwc on 20/05/15.
 */
public class IssueSettings {
  private static final String ISSUE_ENABLED_KEY = "issueUpdate" ;
  private static final String ISSUE_CONDITION = "issueCondition";
  private static final String ISSUE_EMAILS_KEY = "issueEmails";
  private static final String ISSUE_MESSAGE_KEY = "issueMessage";
  private static final String ISSUE_SUBJECT_KEY = "issueSubject";

  private final PluginSettings _settings;

  public IssueSettings(final PluginSettings settings) {
    _settings = settings;
  }

  public boolean isEnabled() {
    Object out = _settings.get(ISSUE_ENABLED_KEY);
    return out == null || out.equals(EmailNotifyPluginConfiguration.BOOLEAN_TRUE);
  }

  public void setEnabled(final boolean enabled) {
    _settings.put(ISSUE_ENABLED_KEY, enabled ? EmailNotifyPluginConfiguration.BOOLEAN_TRUE : EmailNotifyPluginConfiguration.BOOLEAN_FALSE);
  }

  public String getCondition() {
    Object out = _settings.get(ISSUE_CONDITION);

    if (out != null) {
      return (String) out;
    }
    else {
      return "";
    }
  }

  public void setCondition(final String condition) {
    _settings.put(ISSUE_CONDITION, condition);
  }

  public String getRecipients() {
    Object emails = _settings.get(ISSUE_EMAILS_KEY);
    if (emails != null) {
      return (String) emails;
    }
    else {
      return "";
    }
  }

  public void setRecipients(final String emails) {
    _settings.put(ISSUE_EMAILS_KEY, emails);
  }

  public Collection<String> getRecipientsList() {
    final Collection<String> out = Lists.newArrayList();
    for (String email : getRecipients().split(",")) {
      String recipient = email.trim();
      if (!recipient.equals("")) {
        out.add(email.trim());
      }
    }
    return out;
  }

  public String getMessage() {
    Object message = _settings.get(ISSUE_MESSAGE_KEY);
    if (message != null) {
      return (String) message;
    }
    else {
      return "";
    }
  }

  public void setMessage(final String message) {
    _settings.put(ISSUE_MESSAGE_KEY, message);
  }

  public String getSubject() {
    Object subject = _settings.get(ISSUE_SUBJECT_KEY);
    if (subject != null) {
      return (String) subject;
    }
    else {
      return "";
    }
  }

  public void setSubject(String inputField) {
    _settings.put(ISSUE_SUBJECT_KEY, inputField);
  }
}
