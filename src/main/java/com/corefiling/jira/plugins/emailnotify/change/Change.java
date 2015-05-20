package com.corefiling.jira.plugins.emailnotify.change;

/**
 * Created by pwc on 20/05/15.
 */
public interface Change {
  public String getFieldName();
  public String getFrom();
  public String getTo();
}
