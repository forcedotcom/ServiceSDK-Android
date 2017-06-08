## New Features

- Case feed comments are now grouped together if they were posted within 60 seconds of each other
- Authenticated case data is now cached and encrypted using the Salesforce MobileSdk Smartstore. Allows cases to be used while offline. Data is cleared on logout.
- The default UI experience, `case-ui`, now has multi-linugal support for 26 languages.

## API Changes
- Added `returnIntermediateResults` to the core Request classes to allow db results to be returned while network results are pending.
- Added `getCompleteCaseFeed` to `CaseClient` to allow for the feed for a case to be retrieved using a single call to `CaseClient`.
- `CaseClient` will now throw an `IllegalStateException` if account_type is not set. This is done to prevent conflicts between different consumers of the SDK.
 This will result in the return value of `CaseUI.uiClient()` reporting an error. 
- `CaseClient` will now throw an `IllegalStateException` if `SmartstoreSdkManager` is not initialized prior to usage.
This will result in the return value of `CaseUI.uiClient()` reporting an error.
- Added `withUserAccount(UserAccount u)` to `CaseConfiguration` to enable authenticated Cases. By default the case list will be unavailable unless configured to use a user account.

## Upgrade Instructions
- Replace `SalesforceSDKManager.initNative` with `SmartStoreSDKManager.initNative` in your application.
- If not present add the string account_type to your strings.xml
- To use authenticated cases pass a `UserAccount` to your `CaseConfiguration` during setup.
 ```
 new CaseConfiguration.Builder(communityUrl, createCaseQuickActionName).withUserAccount(user)
 ```

## Bug Fixes

- Fixing a bug where unicode characters in Case Details comments were being posted incorrectly.
- Agent case feed comments are no longer grouped together if they were posted by different agents.
- Fixed a bug where case list ordering was impacted by non-viewable fields.

## Notes
- Upgraded to support library 25.3.0. 
- Upgraded MobileSDK version to 5.1.0

## Known Issues
- Case Management: Read-only fields in Case Action layouts display as editable fields when creating a new case using the SDK’s Case Publisher. **Workaround**: Ensure that there are no read-only fields in the Case Action layout you use to create cases with the SDK.