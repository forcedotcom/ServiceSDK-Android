# SOS 4.0.5

## New Features
- Upgraded to OpenTok 2.15.2

## Upgrade Instructions
- When using proguard to compile your app, you may find that sessions do not end on the device
when the agent has ended the session from the Service Console. As a precaution, we recommend
ensuring that the following proguard rules have been added to your build:

```
-keep class com.opentok.** { *; }
-keep class org.webrtc.** { *; }
-keep class com.salesforce.android.sos.** { *; } 
```
