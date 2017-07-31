# Knowledge 2.1.0

## New Features

- Added an API to `knowledge-ui` which allows for providing custom CSS and JavaScript to be used when Knowledge articles are rendered.

## API Changes

- Added the `KnowledgeCssProvider` interface
- Added the `KnowledgeJsProvider` interface
- Added `setCssProvider(KnowledgeCssProvider)` to `KnowledgeUIConfiguration`
- Added `setJsProvider(KnowledgeJsProvider)` to `KnowledgeUIConfiguration`

