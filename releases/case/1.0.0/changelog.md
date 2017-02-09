## New Features
- Added Push notification based on Apex trigger support. You can now call 'CaseConfiguration.Builder#enablePush' and provide an icon to receive push notifications when the application is in the background.
- Upgraded to support library 25.1.0.
- Added swipe to hide case functionality to the case list.
- Added hiding case publisher FAB on scroll down and FAB return on scroll up.
- Added ability to swipe to remove Snackbars.
- Added ability to log case events to Splunk.
- Added snackbar to notify the user when the case list is out of date.
- Added snackbar to allow the user to undo hiding a case.

## API Changes
- Added the 'CaseUIClient#close' method to CaseUIClient api. This method should be called once the CaseUiClient is no longer needed. This is handled automatically if CaseUI is used to restart the client.
- Added Push notification support.
- Replaced usages of ActorModel, BodyModel, ElementModel and PhotoModel in the case-core requests package with Actor, Body, Element, Photo interfaces respectively. 

## Bug Fixes
- Fixed case list crashing on KitKat devices.

## Notes
- Upgraded SalesforceMobileSDK-Android to 5.0.
