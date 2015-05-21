package com.corefiling.jira.plugins.emailnotify.components.version.change;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by pwc on 20/05/15.
 */
public class DateVersionChange extends AbstractVersionChange {
  private final Date _from;
  private final Date _to;

  public DateVersionChange(final String fieldName, final Date from, final Date to) {
    super(fieldName);
    _from = from;
    _to = to;
  }

  private String formatDate(final Date date) {
    if (date == null) {
      return null;
    }
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    df.setTimeZone(TimeZone.getTimeZone("UTC"));
    return df.format(date);
  }

  @Override
  public String getFrom() {
    return formatDate(_from);
  }

  @Override
  public String getTo() {
    return formatDate(_to);
  }
}
