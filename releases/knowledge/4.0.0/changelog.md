# Knowledge 4.0.0

## New Features

- Added ability for app developer to provide and manage authentication for knowledge articles.
- Clearing or deleting the cache via `KnowledgeCore` will now clear or delete the OfflineResourceCache
in addition to the database.

## API Changes
- Removed `KnowledgeClient.cleanup()` and `KnowledgeClient.addUserLogoutListener()` from `knowledge-core` api.
- Replaced `KnowledgeConfiguration.withUserAccount` with `KnowledgeConfiguration.withAuthConfig`.
- Replaced `KnowledgeCore.deleteCacheForUser(Context, UserAccount)` with `KnowledgeCore.deleteCacheForUser(Context, AuthenticatedUser)`
- Replaced `KnowledgeCore.clearCacheForUser(Context, UserAccount)` with `KnowledgeCore.clearCacheForUser(Context, AuthenticatedUser)`
- Changes to `OfflineResourceCache`:
  - Added `OfflineResourceConfig.builder()`, which will return a Builder instance for customizing the cache
  - Added `OfflineResourceConfig.defaults()`, which will return a OfflineResourceCache configured with default values
  - Added `OfflineResourceCache.clearCache(Context, AuthenticatedUser)` for clearing an existing cache
  - Added `OfflineResourceCache.deleteCache(Context, AuthenticatedUser)` for deleting an existing cache
  - Added `OfflineResourceCache.deleteCache(Context)` for deleting existing cache for all users
  - Removed `OfflineResourceConfig.withPath(Context, String)`. Cache files are now created automatically.
  - Removed `OfflineResourceConfig.fromContext(Context)`

## Notes

- Increased Android minimum API support from 19 (KitKat) to 21 (Lollipop)

## Upgrade Instructions

- If assembling with ProGuard you may encounter warnings about missing referenced classes
`javax.annotation.Nullable` and `javax.annotation.ParametersAreNonnullByDefault`. Add the following
`dontwarn` rules to your proguard configuration:

 ```
 -dontwarn javax.annotation.Nullable
 -dontwarn javax.annotation.ParametersAreNonnullByDefault
 ```
