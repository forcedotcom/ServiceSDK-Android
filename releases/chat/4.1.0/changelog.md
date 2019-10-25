# Chat 4.1.0

## New Features
 - Added a new UI configuration flag `allowBackgroundNotifications`, which determines whether the session will post local notifications on didReceiveMessage(chatMessage) chat events.

## API Changes
 - Added ChatUIConfiguration.Builder.allowBackgroundNotifications(boolean) to enable or disable background notifications. Defaults to true.

## Customer-facing Bug Fixes
 - Fixed an issue where versions >=3.12.0 of okhttp in customer's apps could cause a crash during a file transfer.

## Internal Bug Fixes

## Upgrade Instructions

## Known Issues
