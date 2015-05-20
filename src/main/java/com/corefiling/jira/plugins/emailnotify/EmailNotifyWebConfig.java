package com.corefiling.jira.plugins.emailnotify;

import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.corefiling.jira.plugins.emailnotify.components.issue.conf.IssueSettings;
import com.corefiling.jira.plugins.emailnotify.components.version.conf.VersionSettings;
import com.corefiling.jira.plugins.emailnotify.conf.EmailNotifyPluginConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webwork.action.ActionContext;

import java.util.Map;

/**
 * Created by pwc on 15/05/15.
 */
public class EmailNotifyWebConfig extends JiraWebActionSupport {
  private final EmailNotifyPluginConfiguration _settings;
  private final VersionSettings _versionSettings;
  private final IssueSettings _issueSettings;

  private static final String VERSION_EMAIL_KEY = "versionChangesEmails";
  private static final String ISSUE_UPDATE_EMAIL_KEY = "issueRecipients";
  private static final String CONDITION_KEY = "updateCondition";

  public EmailNotifyWebConfig(final EmailNotifyPluginConfiguration settings) {
    _settings = settings;
    _versionSettings = _settings.getVersionSettings();
    _issueSettings = _settings.getIssueSettings();
  }


  private Map getParams() {
    return ActionContext.getContext().getParametersImpl();
  }

  private String getInputField(final String key) {
    final Map params = getParams();
    if (!params.containsKey(key) || params.get(key) == null) {
      return "";
    }
    else {
      Object param = params.get(key);
      if (param instanceof String[]) {
        return ((String[]) param)[0];
      }
      else {
        return "";
      }
    }
  }

  @Override
  protected String doExecute() {
    return INPUT;
  }

  public String doUpdate() {
    ActionContext ctx = ActionContext.getContext();
    Map params = ctx.getParametersImpl();

    // Version changes
    _versionSettings.setEnabled(getParams().containsKey("versionChanges"));
    _versionSettings.setRecipients((getInputField(VERSION_EMAIL_KEY)));

    // Issue update
    _issueSettings.setEnabled(getParams().containsKey("issueUpdated"));
    _issueSettings.setRecipients(getInputField(ISSUE_UPDATE_EMAIL_KEY));
    _issueSettings.setCondition(getInputField(CONDITION_KEY));

    return SUCCESS;
  }

  // template parameters
  // version changes
  public boolean isVersionEnabled() {
    return _versionSettings.isEnabled();
  }

  public boolean isIssueEnabled() {
    return _issueSettings.isEnabled();
  }

  public String versionRecipients() {
    return _versionSettings.getRecipients();
  }

  public String updateRecipients() {
    return _issueSettings.getRecipients();
  }

  public String updateCondition() {
    return _issueSettings.getCondition();
  }

  private static final long serialVersionUID = 1L;
}
