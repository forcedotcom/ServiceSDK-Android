## New Features

## API Changes
- Now targeting Android API level 26 (Android O)
- Support for Notification Channels
- Added ability to specify Push Notification Importance when configuring Case Ui, default is IMPORTANCE_HIGH. Use `CaseUIConfiguration.create(caseConfiguration, IMPORTANCE_LOW)` to configure the importance.
- Changed default notification priority to be PRIORITY_HIGH for API < 26. Priority will be changed to match the IMPORTANCE provided to `CaseUIConfiguration.create(caseConfiguration, IMPORTANCE_LOW)`  
## Upgrade Instructions

## Bug Fixes
- Fixed crash on startup caused when offline cases database cannot be read.

## Notes
- Upgraded MobileSdk from v5.1 to v5.3
- Upgraded Google Play Services to 11.4.2 to support Android O.

## Known Issues


