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
  private static final Logger LOG = LoggerFactory.getLogger("atlassian.plugin");

  public EmailNotifyWebConfig(final EmailNotifyPluginConfiguration settings) {
    _settings = settings;
  }

  @Override
  protected String doExecute() {
    return INPUT;
  }

  public String doUpdate() {
    ActionContext ctx = ActionContext.getContext();
    Map params = ctx.getParametersImpl();

    _settings.setNotifyVersionChanges(params.containsKey("versionChanges"));

    if (!params.containsKey(VERSION_EMAIL_KEY) || params.get(VERSION_EMAIL_KEY) == null) {
      _settings.setNotifyVersionEmails("");
    }
    else {
      String[] emails = (String[]) params.get(VERSION_EMAIL_KEY);
      LOG.info("email-notify: new emails: {}", emails[0]);
      _settings.setNotifyVersionEmails(emails[0]);
    }

    return SUCCESS;
  }

  public boolean isNotifyingVersionChanges() {
    return _settings.getNotifyVersionChanges();
  }

  public String versionChangesEmails() {
    return _settings.getNotifyVersionEmails();
  }

  private static final long serialVersionUID = 1L;
}
