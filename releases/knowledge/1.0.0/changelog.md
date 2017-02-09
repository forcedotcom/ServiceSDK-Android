## New Features

- Added View Additions API. You may now call `KnowledgeUI#viewAddition` with a `KnowledgeViewAddition` instance to add an overlay that floats over one or more scenes within Knowledge UI. Check out the [developer guides](https://developer.salesforce.com/docs/atlas.en-us.noversion.service_sdk_android.meta/service_sdk_android/servicesdk_using_knowledge.htm) for more information.
- Update to support library 25.1.0.

## API Changes

- New View Additions API. Call `KnowledgeUI#viewAddition` to add your `KnowledgeViewAddition` that will appear inside Knowledge UI.

```java
  KnowledgeUI.configure(configuration).viewAddition(myAddition).createClient(this);
```

- `KnowledgeUIClient#setOnCloseListener` is now `addOnCloseListener`.
- Added `removeOnCloseListener` method to `KnowledgeUIClient`.
- Added `close` method to `KnowledgeUIClient`.
- Added ability to specify initial application language and fallback language for articles fetched from the Knowledge Base
The default behavior is to use device language and fallback to english if device language is not specified.
To change this behavior use fallbackLocale and initialLocale from the KnowledgeConfigurationBuilder.
Note that if a language is selected that is not [fully supported](https://help.salesforce.com/articleView?id=faq_getstart_what_languages_does.htm) it will result in an IllegalArgumentException.
If a language is specified but not enabled on the knowledge org the application will use the fallback language.

  ```java
    Locale primaryLanguage = Locale.FRENCH;
    Locale fallbackLanguage = Locale.US;
    KnowledgeConfiguration.Builder builder = KnowledgeConfiguration.builder(communityUrl);
    if (LanguageManager.isValidSfdcLocale(primaryLanguage)) {
      builder.initialLocale(primaryLanguage);
    }
    if (LanguageManager.isValidSfdcLocale(fallbackLanguage)) {
      builder.fallbackLocale(fallbackLanguage);
    }
    KnowledgeConfiguration config = builder.build();
  ```

- Renamed `KnowledgeUIClient#launch` to `KnowledgeUIClient#launchHome`. It now requires an `Activity` instance instead of a `Context`.

- `ArticleDetailRequest` now supports fetching article details with just an ID.

## Bug Fixes

- Fixed search widget expanding to fullscreen in landscape mode.
- Fixed content swatch not appearing in collapsed category/articles that didn't have a hero image.
- Fixed article title being visible in expanded header.
- Cached content will now be returned if there is an error encountered while contacting the online knowledge base.
- Fixed issue with padding disappearing from the bottom of knowledge UI while navigating between scenes.
- Article title + summary now combine to a maximum of three lines in lists.
- Fixed issue with header collapse state not being retained on navigation/rotation.
- Fixed `DataCategoryGroup#getDataCategoryTrees()` returning an empty list when offline.

## Notes
- Increased the minimum supported Android SDK API from 16 to 19

## Known Issues
- Sometimes the incorrect language is returned for categories after switching languages. This will not impact end users there is a change in the device's language settings.

