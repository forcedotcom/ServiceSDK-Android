# Knowledge 4.1.0

## New Features

- Updated caching strategy that improves UI performance when articles have been previously cached.

## Upgrade Instructions

- OkHttp 3.10.0 may require the following proguard rules in your project:
```
-dontwarn okio.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
```

- SqlCipher may require the following proguard rule:
```
-keep class net.sqlcipher.** { *; }
```

## Bug Fixes

* Fixed broken anchor tags within Knowledge article view.

## Known Issues

- The order in which Articles and Categories are displayed on Knowledge UI may differ 
while the device is offline compared to when it is online. 
- Articles and Categories that have been unpublished or re-categorized in your Community
knowledgebase may still appear on Knowledge UI while the device is offline. Unfortunately
at this time, Articles and Categories are not removed from cache.
- Search accuracy is limited to matching terms found within article titles and article
summaries while offline. Online search makes use of the remote server's ability to view
the entirety of the knowledgebase for obtaining search results.

