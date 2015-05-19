package com.corefiling.jira.plugins.emailnotify.util;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.mail.Email;
import com.atlassian.mail.MailFactory;
import com.atlassian.mail.queue.SingleMailQueueItem;
import com.atlassian.mail.server.SMTPMailServer;
import com.corefiling.jira.plugins.emailnotify.email.EmailContent;

/**
 *  Submit an email to JIRA's Email queue.
 */
public class EmailQueuer {
  public static void queueEmail(final String to, final EmailContent content) {
    final SMTPMailServer mailServer = MailFactory.getServerManager().getDefaultSMTPMailServer();
    final Email email = new Email(to);
    email.setFrom(mailServer.getDefaultFrom());
    email.setSubject(content.getSubject());
    email.setMimeType("text/plain");
    email.setBody(content.getBody());
    email.addHeader("X-JIRA-emailnotify-reason", content.getHeader());
    ComponentAccessor.getMailQueue().addItem(new SingleMailQueueItem(email));
  }
}
