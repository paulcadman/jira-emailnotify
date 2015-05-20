package com.corefiling.jira.plugins.emailnotify.components.issue.change;

import com.corefiling.jira.plugins.emailnotify.change.Change;

/**
 * Created by pwc on 20/05/15.
 */
public class IssueChange implements Change {
  private final String _fieldName;
  private final String _from;
  private final String _to;

  public IssueChange(final String fieldName, final String from, final String to) {
    _fieldName = fieldName;
    _from = from;
    _to = to;
  }

  @Override
  public String getFieldName() {
    return _fieldName;
  }

  @Override
  public String getFrom() {
    return _from;
  }

  @Override
  public String getTo() {
    return _to;
  }
}
