# Knowledge 4.2.0

## New Features
- Adding support for Android Pie
- Adding support for hyperlinks between Knowledge articles (Smart links)
- Adding support for viewing images that have been embedded in non-public articles
- Adding a new API to permit launching directly into the details of an article:
  - `KnowledgeUIClient.launchArticle(Context, ArticleSummary)`

## Bug Fixes
- Fixing a problem that prevented Japanese users from searching with a single character
- Improved the accuracy of search results
- Fixed a rare crash that would occur while scrolling through a long list of article summaries while pressing the back button

