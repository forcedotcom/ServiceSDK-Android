# Chat 1.0.0

## New Features

- Added a tracker for messages from the agent while minimized
- Added a pulse animation to the minimized view to indicate the agent is typing
- Added a queue position tracker while the user is queued for a chat agent
- Added support for the `ServiceAnalytics` API in `chat-core`. See the reference documentation for the `ChatAnalytics` class for more information.
- Added a `QueueListener` API to `chat-core` that enables you to receive updates on the user's position in the queue

## Bug Fixes

- Fixing a crash when attempting to display a `DialogFragment` on activities that do not inherit from `FragmentActivity`. Dialogs can not be displayed in this case; instead a warning is logged to the console.
- Fixed a memory leak with the minimized view
- Fixed a crash when the agent disconnects from Omni-Channel during a session
- No longer prompting the user to end the session if they attempt to close the minimized view after a session has already ended.

## Notes
- Increased the minimum supported Android SDK API from 16 to 19

