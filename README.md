# JIRA emailnotify plugin

This plugin adds email notifications for JIRA events.

## Email notifications of create/update/delete for project versions.

An email notification is set to recipients whenever a project email is
created, updated, or deleted. The email contains a link to the project
version page in JIRA.

The notification for updates contains information on the values that have changed.

## Email notifications of updates to issues to the user that made the update.

A condition may be specified as a boolean groovy expression in the plugin configuration.
The JIRA issue object that has been updated (as `issue`) and a `Map` of issue
field names to changes (as `changes`) are both available in the binding of the
expression.

For example:

    issue.statusObject.name == "Open" && changes.Sprint?.to

Means that the condition is satisfied if the issue is in the "Open" state and
its "Sprint" field has changed to be non-empty.

Values of the `changes` Map are Objects with two properties `to` and `from` which contain a `String`
representation of the changes to the field in the update.

An template for the notification email body and subject should also be supplied. The variables
`$issue` and `$changes` are available to the template (these are the same as `issue` and `changes`
for the condition). A variable, `$issueUrl` containing a string representation of the issue URL is also
available. For example:

    $issue.key has been moved to the sprint: 'changes.Sprint.to'.
  
    Please visit $issueUrl for more details.

## Running in the development environment

Assuming you have the Atlassian SDK installed, run the following in the
root of a clone:

    $ atlas-run

## Packaging
Assuming you have the Atlassian SDK installed, run the following in the
root of a clone:

    $ atlas-package

This creates the plugin artefact in the `target/` directory.

## Licensing

This plugin is made available under the Apache 2.0 licence.
