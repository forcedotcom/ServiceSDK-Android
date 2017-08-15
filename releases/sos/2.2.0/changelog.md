# SOS 2.2.0

## New Features
- Added ability to listen to measurements of the agent audio/video stream quality. You can use this interface to listen to statistics relating to the number of packets dropped/received during an SOS session.
Usage Example
 ```
 SosNetworkStatsListener myListener = new SosNetworkStatsListener {
    @Override
    public void onVideoStatsChanged (VideoStats stats) {
        // React to the updates to the video stats here.
     }
    @Override
    public void onAudioStatsChanged (AudioStats stats) {
        // React to the change in the audio stats here.
    }
 };
 Sos.addNetworkStatsListener(myListener);
 // ... sometime later
 Sos.removeNetworkStatsListener(myListener);
```

## Known Issues
- SOS will crash if the SOS session ends before the agent connects when the app is using `SosConfigurationBuilder.builder().connectingUi(false)` on Android N and above. This affects all versions of SOS.


