# SOS 3.0.0

## New Features

- Now targeting Android API level 26 (Android O)
- Support for Notification Channels

## Bug Fixes

- Agent Halo now shows a play icon when it is paused, instead of a pause icon regardless of state
- Updated the back to app bar and information icon animations in the 2 way video mode 
- Fixed a crash that occured when the SosConfigurationBuilder was configured with connectingUi(false)
- Fixed a crash when the name of the Agent did not arrive before the SOS session was connected
- Fixed an existing issue where the device screen appears zoomed on the agent console during screen sharing if a live agent org migration occurs. 
- Fixed a bug where the Mute and 2-way video controls on the Halo Ui disapear when the agent pauses an SOS session. 
- Fixed a bug where the Halo UI would not accept user actions after rotating the device.

## Notes

- Upgraded MobileSdk from v5.1 to v5.3
- Upgraded OpenTok from 2.11.0 to 2.12.0
