## New Features

- Enabling text selection within chat messages

## API Changes

- Adding the following color resources:
    - `salesforce_toolbar` - Toolbar background color
    - `salesforce_toolbar_inverted` - Toolbar text and icon color

- Renaming the following color resources (old IDs now reference the new IDs):
    - `salesforce_brand_contrast` is now `salesforce_brand_primary_inverted`
    - `salesforce_title_text` is now `salesforce_brand_secondary_inverted`

- The `salesforce_brand_primary_darker` color resource is no longer used and may be removed in a later version.

## Bug Fixes

- Fixing a bug that prevented custom fonts from being applied to chat messages
- Changing the chat horizontal line color to `salesforce_contrast_tertiary`
- Fixing bug causing missing ellipsis in text fields.
