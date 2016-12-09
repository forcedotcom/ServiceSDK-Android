## New Features

- Font customization is now supported via Android Styles. To take advantage of
 this feature, perform the following steps:
  1. Add a True Type Font (TTF) file to your project's `assets` directory
  2. Override the `SalesforceFontStyle` style in your project's `styles.xml`
  resource file and set the `salesforceFont` item to the relative asset path of your TTF file. Example:
  ```
    <style name="SalesforceFontStyle">
        <item name="salesforceFont">CustomFont.ttf</item>
    </style>
  ```
- Images from article contents can now be cached to disk by creating an `OfflineResourceConfig` instance and passing it to the `KnowledgeConfiguration`.
- Introducing partial support for Analytics via the `ServiceAnalytics` API. Implement `ServiceAnalyticsListener` to start receiving User-driven events
from the Knowledge UI. More behaviors will be added in the future. See the `KnowledgeUIAnalytics` class Javadoc in the API Reference Documentation for a
list of behaviors and the Map key constants for parsing `eventData`. Example:
  ```
  ServiceAnalytics.addListener(new ServiceAnalyticsListener() {
        @Override
        public void onServiceAnalyticsEvent (String behaviorId, Map<String, Object> eventData) {
        }
      });
  ```
- Error screens now have a brandable icon. The `knowledge_ic_error` drawable resource can be overridden to change this icon. It is tinted with the `salesforce_contrast_tertiary` branding token.
- Added search button to Knowledge Home.

## API Changes

- Repository moved from `http://salesforcesos.com/android/maven/knowledge/sdk/release` to `https://salesforcesos.com/android/maven/release`.
- Artifact Group ID changed from `com.salesforce.android` to `com.salesforce.service`.

### knowledge-core

- Added `OfflineResourceConfig` API.
- Added `OfflineResourceCache` API.
- Added `cacheImages` option to `ArticleDetailsRequest.Builder`.
- Added `offlineResourceConfig` option to `KnowledgeConfiguration.Builder`.
- `ArticleListRequestBuilder#dataCategory` now takes two strings, a group name and a category name, instead of a `DataCategorySummary`.
- Category group name argument removed from `KnowledgeConfiguration#create`.
- Removed `KnowledgeClient#getDataCategoryGroupName` method.
- Removed `KnowledgeConfiguration#getDataCategoryGroupName` method.
- `DataCategoryGroupListRequest` now returns a `DataCategoryGroupList` instead of the internal model class.
- Removed `getDataCategoryName` method from `ArticleList` interface.
- `DataCategoryGroup#getDataCategoryTrees` now returns a list of `DataCategoryTree` instead of the internal model class.

### knowledge-ui

- Removed `KnowledgeUIClient#getCommunityUrl`, `KnowledgeUIClient#getDataCategoryGroupName`, and `KnowledgeUIClient#getRootDataCategory` methods, and added `KnowledgeUIClient#getConfiguration` method.
- `KnowledgeUIConfiguration#create` now requires a `dataCategoryGroupName`.

## Upgrade Instructions

- Modify your `build.gradle` file to change the `repositories` and `dependencies` section to reflect the listed API changes:

```groovy

repositories {
  maven {
    url 'https://salesforcesos.com/android/maven/release'
  }
}

dependencies {
  compile 'com.salesforce.service:knowledge-ui:0.4.0'
}

```

## Bug Fixes

- Knowledge will no longer crash with an `OutOfMemoryException` when trying to batch download many Objects at once.
- The scrolling position is now retained on navigation and rotation.
- Links from article details now open in an external browser.
- The Home modal now has a constrained width in landscape orientation.
- The onClose listener is properly called when the user presses the back button.
- Fixed tint color for various default UI icons.
- Users can now return to search results.

## Known Issues

- Scrollable elements can be drawn below the bottom of the screen when returning to a previously viewed fragment. This can result in items not being visable or being clipped.
This can be corrected by rotating the device or going back one level and reopening the page.
