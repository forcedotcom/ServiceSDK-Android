_This sample app demonstrates Snap-ins features. It doesn't contain production-quality code and is not meant to be used in a production app._

# Snap-Ins Chat Survey

A simple Live Agent Chat app using the Snap-ins SDK that launches a chat session and then redirects the user to a survey page after the session.

* [LaunchChatActivity.java](./app/src/main/java/com/example/salesforce/SnapinsChatSurvey/LaunchChatActivity.java): Configures and initializes a chat session.
* [MyChatListener.java](./app/src/main/java/com/example/salesforce/SnapinsChatSurvey/MyChatListener.java): Listens for Live Agent Chat events. This class handles the end session event and launches the survey.
* [WebViewSurveyActivity.java](./app/src/main/java/com/example/salesforce/SnapinsChatSurvey/WebViewSurveyActivity.java): Activity that displays a web view for the survey. This class isn't necessary if you plan to display a local UI survey.
* [LocalSurveyActivity.java](./app/src/main/java/com/example/salesforce/SnapinsChatSurvey/LocalSurveyActivity.java): Activity that displays a local survey. This class isn't necessary if you plan to display a web-based survey.
* [LocalThankYouActivity.java](./app/src/main/java/com/example/salesforce/SnapinsChatSurvey/LocalThankYouActivity.java): Activity that thanks the user after they complete the local survey. This class isn't necessary if you plan to display a web-based survey.
