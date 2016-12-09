# Cases 0.1.0 - Pilot Release

## Features

- Creating cases with a guest user
- Creating cases with an authenticated Salesforce user
- View a list of cases for an authenticated Salesforce user
- View a case feed with case details
- Post a comment to a case feed
- The `case-core` module offers an API without a pre-package UI allowing customers
to create their own
- The `case-ui` module conforms with the Service SDK branding token scheme to
enable color customization of the pre-packaged Case UI.

## Quick-Start Integration

- Minimum supported Android API: 19, KitKat
- For integration with the pre-packaged Cases UI, add the following configuration
to your project's Gradle file:

```
repositories {
  maven { url 'https://salesforcesos.com/android/maven/release' }
}

dependencies {
  compile 'com.salesforce.service:case-ui:0.1.0'
}
```

- To launch the Case UI from your application you must first configure the Case Management
in your Salesforce Organization and obtain the following information:
  - Salesforce Community URL
  - Create Case Quick Action Name
  - Case List Developer Name *(optional, only necessary if you want to be able to view a list of
  cases and create cases for an authenticated user)*

- Once you've obtained this information, you must
create a new `CaseConfiguration` object followed by a `CaseUIConfiguration`
 object:

```
CaseConfiguration caseConfiguration =
  new CaseConfiguration.Builder(COMMUNITY_URL, CREATE_CASE_ACTION_NAME).build();

CaseUIConfiguration caseUIConfiguration = CaseUIConfiguration.create(caseConfiguration);
```

- Pass the `CaseUIConfiguration` to `CaseUI.with(applicationContext).configure(..)`, call
`.uiClient()` and handle the `CaseUIClient` result asynchronously.
You may then launch the Case UI by calling
`CaseUIClient.launch(Activity)`:

```
CaseUI.with(getApplicationContext()).configure(caseUIConfiguration)
  .uiClient()
  .onResult(new Async.ResultHandler<CaseUIClient>() {
    @Override public void handleResult(Async<?> operation, @NonNull CaseUIClient caseUIClient) {
      // Call launch(..) on the caseUIClient to launch the Case UI
      caseUIClient.launch((Activity) activity);
    }
  });
```

- This will launch the Case Publisher UI which will allow a guest user to create a case
- If you would like to allow authenticated Salesforce users to view a list of their cases,
post comments to their case feed, and create cases you will need to authenticate with the
Salesforce Mobile SDK. Please refer to the Salesforce Mobile SDK documentation on how to
authenticate with Salesforce.
- Once you have added the code required to authenticate your users, you will need to provide
 the Case List Name when configuring the `CaseConfiguration`, like this:

```
CaseConfiguration caseConfiguration =
  new CaseConfiguration.Builder(COMMUNITY_URL, CREATE_CASE_ACTION_NAME)
    .caseListName(CASE_LIST_NAME)
    .build();
```

- The `caseListName` is the developer name of the case list you would like to show for your
authenticated Salesforce user.
- Upon doing so, and if the Case UI detects your authenticated Salesforce user, calls to
`CaseUIClient.launch(..)` will launch the list of cases for your user.

## Integration with Case Core (Headless API)

- For integration *without* the pre-packaged UI, depend on `case-core:0.1.0`
instead. You can then retrieve a `CaseClient` to begin making API calls, like this:

```
CaseConfiguration caseConfiguration =
  new CaseConfiguration.Builder(COMMUNITY_URL, CREATE_CASE_ACTION_NAME)
    .caseListName(CASE_LIST_NAME)
    .build();
CaseCore.configure(caseConfiguration)
          .createClient(context)
          .onResult(new Async.ResultHandler<CaseClient>() {
            @Override public void handleResult(Async<?> operation, @NonNull CaseClient caseClient) {
              // you now have a CaseClient that you can use to make API calls
            }
          });
```

## Known Limitations and Issues

- Deleting/hiding cases in case list is not yet supported
- Languages other than English are not yet supported