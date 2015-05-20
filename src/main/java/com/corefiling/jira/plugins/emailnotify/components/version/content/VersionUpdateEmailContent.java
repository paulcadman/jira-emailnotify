package com.corefiling.jira.plugins.emailnotify.components.version.content;

import com.atlassian.jira.event.project.VersionUpdatedEvent;
import com.atlassian.jira.project.version.Version;
import com.corefiling.jira.plugins.emailnotify.change.Change;
import com.corefiling.jira.plugins.emailnotify.components.version.change.DateVersionChange;
import com.corefiling.jira.plugins.emailnotify.components.version.change.StringVersionChange;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.util.Collection;

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

    if (!getVersion().getStartDate().equals(getOriginalVersion().getStartDate())) {
      changes.add(new DateVersionChange("Start date", getOriginalVersion().getStartDate(), getVersion().getStartDate()));
    }

    if (!getVersion().getReleaseDate().equals(getOriginalVersion().getReleaseDate())) {
      changes.add(new DateVersionChange("Release date", getOriginalVersion().getReleaseDate(), getVersion().getReleaseDate()));
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

  @Override
  protected String getEventType() {
    return "UPDATED";
  }
}
