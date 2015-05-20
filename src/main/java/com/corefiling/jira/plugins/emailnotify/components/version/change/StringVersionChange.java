package com.corefiling.jira.plugins.emailnotify.components.version.change;

/**
 * Created by pwc on 20/05/15.
 */
public class StringVersionChange extends AbstractVersionChange {
  private final String _from;
  private final String _to;

  public StringVersionChange(final String fieldName, final String from, final String to) {
    super(fieldName);
    _from = from;
    _to = to;
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
