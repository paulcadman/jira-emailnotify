package com.corefiling.jira.plugins.emailnotify;

import com.atlassian.jira.web.action.JiraWebActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webwork.action.ActionContext;

import java.util.Map;

/**
 * Created by pwc on 15/05/15.
 */
public class EmailNotifyWebConfig extends JiraWebActionSupport {
  private final EmailNotifyPluginConfiguration _settings;
  private static final String VERSION_EMAIL_KEY = "versionChangesEmails";
  private static final String TRIAGE_IN_SPRINT_EMAIL_KEY = "triageInSprintEmails";
  private static final Logger LOG = LoggerFactory.getLogger("atlassian.plugin");

  public EmailNotifyWebConfig(final EmailNotifyPluginConfiguration settings) {
    _settings = settings;
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
    _settings.setNotifyVersionChanges(getParams().containsKey("versionChanges"));
    _settings.setNotifyVersionEmails(getInputField(VERSION_EMAIL_KEY));

    // triage in sprint
    _settings.setNotifyTriageInSprint(getParams().containsKey("triageInSprint"));
    _settings.setNotifyTriageInSprintEmails(getInputField(TRIAGE_IN_SPRINT_EMAIL_KEY));

    return SUCCESS;
  }

  // template parameters
  // version changes
  public boolean isNotifyingVersionChanges() {
    return _settings.getNotifyVersionChanges();
  }

  public String versionChangesEmails() {
    return _settings.getNotifyVersionEmails();
  }

  // triage in sprint
  public boolean isNotifyingTriageInSprint() {
    return _settings.getNotifyTriageInSprint();
  }

  public String triageInSprintEmails() {
    return _settings.getNotifyTriageInSprintEmails();
  }

  private static final long serialVersionUID = 1L;
}
