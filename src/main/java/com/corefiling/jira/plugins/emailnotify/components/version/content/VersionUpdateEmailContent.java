package com.corefiling.jira.plugins.emailnotify.components.version.content;

import com.atlassian.jira.event.project.VersionUpdatedEvent;
import com.atlassian.jira.project.version.Version;
import com.corefiling.jira.plugins.emailnotify.change.Change;
import com.corefiling.jira.plugins.emailnotify.components.version.change.DateVersionChange;
import com.corefiling.jira.plugins.emailnotify.components.version.change.StringVersionChange;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Date;

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

  @Override
  protected Version getVersion() {
    return _updatedEvent.getVersion();
  }

  private Collection<Change> getVersionChanges() {
    Collection<Change> changes = Lists.newArrayList();

    if (!getVersion().getName().equals(getOriginalVersion().getName())) {
      changes.add(new StringVersionChange("Name", getOriginalVersion().getName(), getVersion().getName()));
    }

    final Date newStartDate = getVersion().getStartDate();
    final Date oldStartDate = getOriginalVersion().getStartDate();
    if (newStartDate == null && oldStartDate != null) {
      changes.add(new DateVersionChange("Start date", oldStartDate, null));
    }
    else if (newStartDate != null && oldStartDate == null) {
      changes.add(new DateVersionChange("Start date", null, newStartDate));
    }
    else if (newStartDate == null) {
      changes.add(new DateVersionChange("Start date", null, null));
    }
    else if (!newStartDate.equals(oldStartDate)) {
      changes.add(new DateVersionChange("Start date", oldStartDate, newStartDate));
    }

    final Date newReleaseDate = getVersion().getReleaseDate();
    final Date oldReleaseDate = getOriginalVersion().getReleaseDate();
    if (newReleaseDate == null && oldReleaseDate != null) {
      changes.add(new DateVersionChange("Release date", oldReleaseDate, null));
    }
    else if (newReleaseDate != null && oldReleaseDate == null) {
      changes.add(new DateVersionChange("Release date", null, newReleaseDate));
    }
    else if (newReleaseDate == null) {
      changes.add(new DateVersionChange("Release date", null, null));
    }
    else if (!newReleaseDate.equals(oldReleaseDate)) {
      changes.add(new DateVersionChange("Release date", oldReleaseDate, newReleaseDate));
    }

    return changes;
  }

  private String getVersionChangesString() {
    StringBuilder sb = new StringBuilder();
    for (Change change : getVersionChanges()) {
      if (change.getFrom() == null && change.getTo() == null) {
        sb.append(String.format("'%s' cleared\n", change.getFieldName()));
      }
      else if (change.getFrom() == null) {
        sb.append(String.format("'%s' changed from unset to %s\n", change.getFieldName(), change.getTo()));
      }
      else if (change.getTo() == null) {
        sb.append(String.format("'%s' changed from %s to unset\n", change.getFieldName(), change.getFrom()));
      }
      else {
        sb.append(String.format("'%s' changed from %s to %s\n", change.getFieldName(), change.getFrom(), change.getTo()));
      }
    }
    return sb.toString();
  }

  @Override
  public String getBody() {
    return String.format("%s\n\n%s\n%s", getMessageStart(), getVersionChangesString(), getMessageEnd());
  }

  @Override
  protected String getEventType() {
    return "UPDATED";
  }
}
