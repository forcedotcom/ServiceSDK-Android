## New Features
- Replaced sqlLite database with Smartstore database.
- Offline Resources stored in the `OfflineResourceCache` are now encrypted.
- Added ability to use authenticated community articles. To use authenticated Kb add a user to the KnowledgeCore client.
`
KnowledgeConfiguration.builder(communityUrl).withUserAccount(accountToUse).build();
`

## API Changes
- Requires Mobile SDK to be initialized in order to use knowledge.
- Requires the account_type string resource to be set in order to use knowledge.
- Added addKbUserLogoutListener(LogoutObserver o) to KnowledgeClient to allow the application to be notified on the kb user logout.
- Added cleanup() to KnowledgeClient to allow properly free resources when a KnowledgeClient for an Authenticated user is no longer needed.
- Added withUserAccount(UserAccount u) to KnowledgeConfiguration to enable authenticated knowledge.
- Changed clearCache(Context c, String locale) to clearCacheForUser(Context c, UserAccount a) in KnowledgeCore.
- Changed deleteCache(Context c, String locale) to deleteCache(Context c) in KnowledgeCore.

## Upgrade Instructions
- Add the following line to the onCreate for your application.

`
SmartStoreSDKManager.initNative(this, new SmartStoreSDKManager.KeyInterface() {
      @Override public String getKey (String name) {
        //Your key generator here.
      }
    }, <YourMainActivityClass>.class);
`
- Add the following string resource to your application.
`
<string name="account_type">your.application.unique.identifier</string>
`

## Bug Fixes
- Fixed a bug where article summaries might not be displayed within an article list.
- Improved support for dark color schemes. Please see the [UI Customization Guide](https://developer.salesforce.com/docs/atlas.en-us.noversion.service_sdk_android.meta/service_sdk_android/android_customize_colors.htm) for more information.
- Improved accessibility support by updating touch areas, contrast ratios, and content descriptions.
- Fixed a bug where the keyboard would hide in the search view when rotating the device.

## Notes
- Upgraded Support Libraries to 25.2.0
- Moved logic for caching into common to share with Cases.
- Added dependency on Mobile Sdk for Smartstore.
- Required proguard rules for knowledge-core updated.
- When using an authenticated user with KB the UI will automatically close if the authenticated user is logged out. 

## Known Issues

## Functional Tests Required
