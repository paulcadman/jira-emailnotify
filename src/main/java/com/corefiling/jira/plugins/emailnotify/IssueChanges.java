package com.corefiling.jira.plugins.emailnotify;

import com.atlassian.jira.event.issue.IssueEvent;
import org.ofbiz.core.entity.GenericEntityException;
import org.ofbiz.core.entity.GenericValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public class IssueChanges {
  private static final Logger LOG = LoggerFactory.getLogger("atlassian.plugin");
  private final IssueEvent _issueEvent;

  public IssueChanges(final IssueEvent issueEvent) {
    _issueEvent = issueEvent;
  }

  public boolean isFieldChanged(final String fieldName, final String fieldValue) throws GenericEntityException{
    GenericValue changeItem;
    String currentFieldName;

    final List<GenericValue> changeItemList = _issueEvent.getChangeLog().getRelated("ChildChangeItem");

    Iterator<GenericValue> changeItemListIterator = changeItemList.iterator();
    while (changeItemListIterator.hasNext()) {
      changeItem = (GenericValue) changeItemListIterator.next();
      currentFieldName = changeItem.get("field").toString();

      //LOG.info("Field {} has changed from {} to {}.", currentFieldName, changeItem.get("oldstring"), changeItem.get("newstring"));
      LOG.info("Field {} has been changed {}.", currentFieldName, changeItem.get("oldstring") + "->" + changeItem.get("newstring"));
      if (currentFieldName.equals(fieldName)) {
        Object oldValue = changeItem.get("oldstring");
        Object newValue = changeItem.get("newstring");
        if (oldValue != null && newValue != null && !oldValue.equals(newValue) && newValue.equals(fieldValue)) {
          return true;
        }
        else if (oldValue == null && newValue != null && newValue.equals(fieldValue)) {
          return true;
        }
      }
    }
    return false;
  }
}
