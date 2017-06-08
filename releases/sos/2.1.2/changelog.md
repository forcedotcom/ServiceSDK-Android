# SOS 2.1.2

## Upgrade Instructions

- To remove the presence of the READ_PHONE_STATE permission on Android 6.0 and later devices, set the max SDK
attribute for the permission in your app's manifest:

```
    <uses-permission
        android:name="android.permission.READ_PHONE_STATE"
        android:maxSdkVersion="19"/>
```

## Bug Fixes
- Fixed crash when multi-touching near the edges of the halo ui bounding box.
- Removing unnecessary permission request for READ_PHONE_STATE on Android 6.0 and later devices
- Improving accessibility on the Camera UI, specifically during Field Service mode.
- Fixed crash when Sos session is quickly paused/unpaused.

## Known Issues
- Android's TalkBack Accessibility feature is not supported during screen sharing.

## Notes
- Upgraded OpenTok version to 2.11.0
