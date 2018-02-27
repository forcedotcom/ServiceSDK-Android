# SOS 4.0.0 

## API Changes
- Deprecated `SosConfiguration.builder()` method. Use `new SosConfiguration.Builder()` instead.

## Bug Fixes

- Fixing a crash on some Lollipop devices that would occur during a screen orientation change on the Onboarding view

## Notes
- Increased Android minimum API support from 19 (KitKat) to 21 (Lollipop)
- Upgrading to OpenTok 2.12.1

## Upgrade Instructions

- If assembling with ProGuard you may encounter warnings about missing referenced classes
`javax.annotation.Nullable` and `javax.annotation.ParametersAreNonnullByDefault`. Add the following
`dontwarn` rules to your proguard configuration:

 ```
 -dontwarn javax.annotation.Nullable
 -dontwarn javax.annotation.ParametersAreNonnullByDefault
 ```

