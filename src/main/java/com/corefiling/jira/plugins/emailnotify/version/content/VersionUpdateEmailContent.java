package com.corefiling.jira.plugins.emailnotify.version.content;

import com.atlassian.jira.event.project.VersionUpdatedEvent;
import com.atlassian.jira.project.version.Version;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by pwc on 19/05/15.
 */
public class VersionUpdateEmailContent extends AbstractVersionEmailContent {
  private final VersionUpdatedEvent _updatedEvent;

  public VersionUpdateEmailContent(final VersionUpdatedEvent event) {
    super(event);
    _updatedEvent = event;
  }

  private Version getOriginalVersion() {
    return _updatedEvent.getOriginalVersion();
  }

  private Version getVersion() {
    return _updatedEvent.getVersion();
  }

  private Collection<Change> getVersionChanges() {
    Collection<Change> changes = Lists.newArrayList();

    if (!getVersion().getName().equals(getOriginalVersion().getName())) {
      changes.add(new NameChange("Name", getOriginalVersion().getName(), getVersion().getName()));
    }

    if (!getVersion().getStartDate().equals(getOriginalVersion().getStartDate())) {
      changes.add(new DateChange("Start date", getOriginalVersion().getStartDate(), getVersion().getStartDate()));
    }

    if (!getVersion().getReleaseDate().equals(getOriginalVersion().getReleaseDate())) {
      changes.add(new DateChange("Release date", getOriginalVersion().getReleaseDate(), getVersion().getReleaseDate()));
    }

    return changes;
  }

  private String getVersionChangesString() {
    StringBuilder sb = new StringBuilder();
    for (Change change : getVersionChanges()) {
      sb.append(String.format("'%s'%s changed from %s to %s\n", change.getFieldName(), Strings.repeat(" ", "Release date".length() - change.getFieldName().length()), change.getFrom(), change.getTo()));
    }
    return sb.toString();
  }

  @Override
  public String getBody() {
    return String.format("%s\n\n%s\n%s", getMessageStart(), getVersionChangesString(), getMessageEnd());
  }

  public String getEventType() {
    return "UPDATED";
  }

  private interface Change {
    public String getFieldName();
    public String getFrom();
    public String getTo();
  }

  private abstract class AbstractChange implements Change {
    private final String _fieldName;

    public AbstractChange(final String fieldName) {
      _fieldName = fieldName;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  private class NameChange extends AbstractChange {
    private final String _from;
    private final String _to;

    public NameChange(final String fieldName, final String from, final String to) {
      super(fieldName);
      _from = from;
      _to = to;
    }

    public String getFrom() {
      return _from;
    }

    public String getTo() {
      return _to;
    }
  }

  private class DateChange extends AbstractChange {
    private final Date _from;
    private final Date _to;

    public DateChange(final String fieldName, final Date from, final Date to) {
      super(fieldName);
      _from = from;
      _to = to;
    }

    private String formatDate(final Date date) {
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      df.setTimeZone(TimeZone.getTimeZone("UTC"));
      return df.format(date);
    }

    public String getFrom() {
      return formatDate(_from);
    }

    public String getTo() {
      return formatDate(_to);
    }
  }
}
