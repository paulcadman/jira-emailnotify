package com.corefiling.jira.plugins.emailnotify.components.version.change;

import com.corefiling.jira.plugins.emailnotify.change.Change;

/**
 * Created by pwc on 20/05/15.
 */
public abstract class AbstractVersionChange implements Change {
  private final String _fieldName;

  public AbstractVersionChange(final String fieldName) {
    _fieldName = fieldName;
  }

  public String getFieldName() {
    return _fieldName;
  }
}
