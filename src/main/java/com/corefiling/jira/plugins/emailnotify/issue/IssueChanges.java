package com.corefiling.jira.plugins.emailnotify.issue;

import com.atlassian.jira.event.issue.IssueEvent;
import com.google.common.base.Optional;
import org.ofbiz.core.entity.GenericEntityException;
import org.ofbiz.core.entity.GenericValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public class IssueChanges {
  private static final Logger LOG = LoggerFactory.getLogger("atlassian.plugin");

  public static Optional<String> getFieldChange(final IssueEvent issueEvent, final String fieldName) {
    final GenericValue changeLog = issueEvent.getChangeLog();
    if (changeLog == null) {
      // only comments were changed
      return Optional.absent();
    }

    try {
      for (GenericValue changeItem: changeLog.getRelated("ChildChangeItem")) {
        String currentFieldName = (String) changeItem.get("field");
        if (currentFieldName == null) {
          continue;
        }

        if (currentFieldName.equals(fieldName)) {
          String newValue = (String) changeItem.get("newstring");
          if (newValue == null) {
            return Optional.absent();
          }
          else {
            return Optional.of(newValue);
          }
        }
      }
    } catch (GenericEntityException e) {
      LOG.warn(e.toString());
    }

    return Optional.absent();
  }
}
