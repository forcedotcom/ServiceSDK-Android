_This sample app demonstrates Snap-ins features. It doesn't contain production-quality code and is not meant to be used in a production app._

# Snap-Ins SDK Hello World Example

A simple Snap-ins SDK Hello World app that launches Knowledge, Cases, Chat, and SOS. This app uses a `KnowledgeViewAddition` to display floating action buttons.

* [SnapinsLauncher.java](./app/src/main/java/com/salesforce/snapinssdkexample/SnapinsLauncher.java): This class configures and launches all the Snap-ins features. Fill in the `TODO` items in this class before running the app. Update the `startSnapins` method if you don't want Knowledge to launch first.
* [SnapinsApplication.java](./app/src/main/java/com/salesforce/snapinssdkexample/SnapinsApplication.java): This class initializes the Salesforce Mobile SDK. Be sure to update the `getKey` method in this class.
* [SnapinsViewAddition.java](./app/src/main/java/com/salesforce/snapinssdkexample/SnapinsViewAddition.java): This class handles the floating action buttons in the Knowledge experience.
* [SnapinsChatSessionListener.java](https://github.com/forcedotcom/ServiceSDK-Android/blob/master/Examples/SnapinsSDKExample/app/src/main/java/com/salesforce/snapinssdkexample/SnapinsChatSessionListener.java): Listens for Live Agent Chat events.
