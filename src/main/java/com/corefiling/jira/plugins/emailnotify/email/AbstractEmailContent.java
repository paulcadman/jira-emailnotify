package com.corefiling.jira.plugins.emailnotify.email;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.properties.APKeys;

/**
 * Created by pwc on 19/05/15.
 */
public abstract class AbstractEmailContent implements EmailContent {
  public String getBaseUrl() {
    return ComponentAccessor.getApplicationProperties().getString(APKeys.JIRA_BASEURL);
  }
}
