package com.corefiling.jira.plugins.emailnotify.email;

/**
 * Created by pwc on 19/05/15.
 */
public interface EmailContent {
  public String getSubject();
  public String getBody();
  public String getHeader();
}
