## New Features

## API Changes
- Added `ArticleWebView` API to `knowledge-ui` allowing articles to be launched in a WebView using a KnowledgeClient without navigating the Knowledge tree.

## Bug Fixes

- Fixed a bug where long Data Category titles would overlap with the expand/collapse chevron.
- Fixed bug causing offline caching to break when using the core API to fetch articles directly for known IDs (i.e. when not using an `ArticleListRequest`.)

## Notes

- Upgraded MobileSdk from v5.1 to v5.3

## Known Issues

- When caching content for offline use the log may show "Error inserting <redacted values> using <redacted sql> into <TableName>". There is no impact on the application behavior beyond the log statement.