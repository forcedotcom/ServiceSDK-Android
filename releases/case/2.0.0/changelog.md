## New Features

- Case feed comments are now grouped together if they were posted within 60 seconds of each other
- Authenticated case data is now cached and encrypted. Allows case to be used while offline. Data is cleared on logout.
- The default UI experience, `case-ui`, now has multi-linugal support for 26 languages.

## API Changes
- Deleted CommunityIdRequest API.
- Added returnIntermediateResults to the core Request classes to allow db results to be returned while network results are pending.
- Added getCompleteCaseFeed to CaseClient to allow for the feed for a case to be retrieved using a single call.
- CaseClient will now throw an IllegalStateException if account_type is not set. This is done to prevent conflicts between different consumers of the SDK.
 This will result in the return value of CaseUI.uiClient() reporting an error. 
- CaseClient will now throw an IllegalStateException if SmartstoreSdkManager is not initialized prior to usage.
This will result in the return value of CaseUI.uiClient() reporting an error.
- Added withUserAccount(UserAccount u) to CaseConfiguration to enable authenticated Cases. By default the case list will be unavailable unless configured to use a user account.
## Upgrade Instructions

## Bug Fixes

- Fixing a bug where unicode characters in Case Details comments were being posted incorrectly.
- Agent case feed comments are no longer grouped together if they were posted by different agents.

## Notes
- Removed dependency on internal SalesforceSDK and SalesforceAnalytics projects.
- Added dependency on [SalesforceSDK 5.0.0](https://bintray.com/forcedotcom/salesforcemobilesdk/salesforce-sdk)
- Upgraded to support library 25.1.0. 
- Upgraded MobileSDK version to 5.1.0
- Changed FetchSaveOp to be shared in common with KB.
## Known Issues


