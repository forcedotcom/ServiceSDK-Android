# Case 4.1.0

## New Features

- Updated caching strategy that improves UI performance when content has been previously cached.

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

