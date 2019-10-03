# SOS <version>

## New Features
 - Upgraded the OpenTok SDK to version 2.16.2

## API Changes


## Customer-facing Bug Fixes


## Internal Bug Fixes


## Upgrade Instructions

 - A dependency in this SDK now uses Java 8 language features. Include the following in your app's `build.gradle` file under the `android` section:

 ```
compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
}
 ```

## Known Issues


