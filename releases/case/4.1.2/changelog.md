# Case 4.1.2 

## New Features
- Adding support for Android Pie 

## Upgrade Instructions

- If you require our `connected-app` module to consume APEX Push Notifications, please note that we
have migrated from GCM to Firebase Messaging. This requires modifications to your app.
    1. Follow the guide for adding Firebase to your app: https://firebase.google.com/docs/android/setup
    2. Remove any references to GCM in your build dependencies and your `AndroidManifest.xml` files.
    3. If you experience a `NoClassDefError` which claims that `GcmReceiver` is missing, it may be
    included in your final merged manifest by one of your dependencies such as the Salesforce Mobile SDK.
    Add the following `remove` instruction to your `AndroidManifest.xml` to solve the error:
        ```xml
        <receiver
            android:name="com.google.android.gms.gcm.GcmReceiver"
            tools:node="remove"/>
        ```
    4. Update your `connected-app` dependency version to `7.1.0`
    5. Update your `PushNotificationListener.onPushNotificationReceived(Bundle)` method to receive a
    `RemoteMessage` parameter instead of `Bundle`. See the `RemoteMessage` reference documentation for
    more information on how to read data from this new type:
    https://firebase.google.com/docs/reference/android/com/google/firebase/messaging/RemoteMessage

## Bug Fixes
- Fixed a problem where picklist options would display in alphabetical order

