package com.corefiling.jira.plugins.emailnotify.util;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.mail.Email;
import com.atlassian.mail.MailFactory;
import com.atlassian.mail.queue.SingleMailQueueItem;
import com.atlassian.mail.server.SMTPMailServer;

/**
 *  Submit an email to JIRA's Email queue.
 */
public class EmailQueuer {
  public static void queueEmail(final String to, final String subject, final String body, final String reason) {
    final SMTPMailServer mailServer = MailFactory.getServerManager().getDefaultSMTPMailServer();
    final Email email = new Email(to);
    email.setFrom(mailServer.getDefaultFrom());
    email.setSubject(subject);
    email.setMimeType("text/plain");
    email.setBody(body);
    email.addHeader("X-JIRA-emailnotify-reason", reason);
    ComponentAccessor.getMailQueue().addItem(new SingleMailQueueItem(email));
  }
}
