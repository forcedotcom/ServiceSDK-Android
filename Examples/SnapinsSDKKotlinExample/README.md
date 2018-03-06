# Snap-Ins SDK Kotlin Example Application


An example implementation of the Snap-ins SDK using [Kotlin](https://kotlinlang.org/).

See the [Developer Documentation](https://developer.salesforce.com/docs/atlas.en-us.noversion.service_sdk_android.meta/service_sdk_android/servicesdk_android_dev_guide.htm)
for more detailed implementation instructions.


## Required Configurations

### Settings Activities

The configuration values (organization id, deployment id, pod url, etc) for the Snap-ins SDKs can be
customized by accessing the settings activities from the Main Activity toolbar. The default values
for these configurations are stored in [strings.xml](app/src/main/res/values/strings.xml).

### Configuration Strings

Update the required string configurations in [strings.xml](app/src/main/res/values/strings.xml) with
valid data to ensure the Snap-ins SDK will function as expected.


### Using Authentication

Developers can either:
* Provide a valid domain in [servers.xml](app/src/main/res/xml/servers.xml).
* Use the provided default <a href="https://login.salesforce.com">login.salesforce.com</a> domain 
and add a custom authentication domain at runtime using the menu ("change server" -> "Add Connection").

See the [Developer Documentation](https://developer.salesforce.com/docs/atlas.en-us.noversion.service_sdk_android.meta/service_sdk_android/android_knowledge_auth_setup.htm) 
for more detailed implementation instructions regarding authentication.

### Push Notifications for Cases

In order to use push notifications for case management, provide valid data in [bootconfig.xml](app/src/main/res/values/bootconfig.xml).
Using [Salesforce’s push notification implementation guide](https://developer.salesforce.com/docs/atlas.en-us.noversion.service_sdk_android.meta/service_sdk_android/android_case_push_notification.htm), 
you can send notifications from your org when activity associated with a user’s case occurs. After 
you’ve set up notifications in your org, handle the notification from your app.


## What's Included


### Knowledge

The Knowledge feature in the SDK gives you access to your org’s knowledge base directly from within 
your app. Once you point your app to your community URL with the right category group and root data 
category, you can display your knowledge base to your users.

The Knowledge UI is launched from the [Main Activity](app/src/main/java/com/salesforce/snapinssdkexample/activities/MainActivity.kt)
of the [app](app/) module.


### Case Management

The Case Management feature in the SDK allows your users to create and manage cases directly from 
your mobile app. Once you point your app to your community URL, you can display the case publisher
interface to your users.

Cases can be launched from the FAB speed dial button after launching the Knowledge UI from the Main Activity.


### Live Agent Chat

With Live Agent Chat, you can provide real-time chat sessions from within your native app. 
Once you’ve set up Live Agent Chat for Service Cloud, it takes just a few calls to the SDK to have 
your app ready to handle agent chat sessions.

Live Agent Chat can be launched from the FAB speed dial button after launching the Knowledge UI from
the Main Activity.


### SOS

SOS lets you easily add real-time video and screen sharing support to your native Android app. 
Once you’ve set up Service Cloud for SOS, it takes just a few calls to the SDK to have your app 
ready to handle agent calls and to support screen sharing. With screen sharing, agents can even make 
annotations directly on the customer’s screen.

SOS can be launched from the FAB speed dial button after launching the Knowledge UI from the Main
Activity.
