# Chat 3.0.0

## New Features

- Adding support for Agent Transfer
- Adding support for Chatbots
- Adding new Chat Analytics for file transfers (requested, initiated, completed, failed, cancelled)
- Adding a new Chat UI configuration option for hiding a customer's queue position while waiting for
an agent: `ChatUIConfiguration.Builder.hideQueuePosition(boolean)`

## Bug Fixes

- Fixed chat file transfer for remote images not saved on the device.
- Improving TalkBack support on the minimized view during an active chat session.
- Fixed unresponsive chat dialog "end session" button
- Fixed a bug that prevented a Chat session from ending promptly by the user if network connection had been lost.

## Notes
- Increased Android minimum API support from 19 (KitKat) to 21 (Lollipop)

## Upgrade Instructions

- If assembling with ProGuard you may encounter warnings about missing referenced classes
`javax.annotation.Nullable` and `javax.annotation.ParametersAreNonnullByDefault`. Add the following
`dontwarn` rules to your proguard configuration:

 ```
 -dontwarn javax.annotation.Nullable
 -dontwarn javax.annotation.ParametersAreNonnullByDefault
 ```
