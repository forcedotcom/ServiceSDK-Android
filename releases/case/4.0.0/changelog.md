# Case 4.0.0

## API Changes

- Added `notifyCaseUpdated(String)` to `CaseUIClient`, which accepts a Case ID as a string. This enables
an application to handle push notifications with their own implementation.
- Removed `CaseUIConfiguration.create(CaseConfiguration, int)` for specifying notification importance. CaseUI no longer creates Android Notifications.
- Removed `enablePush(int)` from `CaseConfiguration` along with the `isPushEnabled()` and `getNotificationIcon()` getters
- Removed `CaseClientCallbacks.handleBackgroundNotification(Bundle)`
- Removed `CaseClientCallbacks.handleForegroundNotification(Bundle)`
- `CreateCaseQuickActionRequest.getResource()` has been renamed to `CreateCaseQuickActionRequest.getQuickActionName()`
- `CaseConfiguration.Builder.withUserAccount(@Nullable UserAccount userAccount)` has been replaced with `CaseConfiguration.Builder.withAuthConfig(@NonNull AuthTokenProvider provider, @NonNull AuthenticatedUser user)`
- 'CaseUiClient.isAuthenticated()' has been removed.

## Bug Fixes

- Fixed an issue where where the post button remained enabled after posting a message to the case feed.

## Notes
- Increased Android minimum API support from 19 (KitKat) to 21 (Lollipop)

- Case SDK no longer depends on Salesforce Mobile SDK for authentication. If your use case requires user authentication
please consult the Snap-Ins Developer Guides for detailed upgrade instructions.
- Case SDK no longer offers built-in support for Connected App Push Notifications. If your use case requires push
notifications please consult the Snap-Ins Developer Guides for detailed upgrade instructions.

## Upgrade Instructions

- If assembling with ProGuard you may encounter warnings about missing referenced classes
`javax.annotation.Nullable` and `javax.annotation.ParametersAreNonnullByDefault`. Add the following
`dontwarn` rules to your proguard configuration:

 ```
 -dontwarn javax.annotation.Nullable
 -dontwarn javax.annotation.ParametersAreNonnullByDefault
 ```
