# Chat 2.0.0

## New Features

- Chat sessions will now attempt to reconnect to the server in the event of a connection interruption.
- Now targeting Android API level 26 (Android O)
- Support for Notification Channels
- Support for Sensitive Data Rules, as configured in your Salesforce Organization. This is used to maintain the privacy of your users by
scrubbing their outgoing message text for sensitive data before they are transmitted over the network.

## API Changes

- The `PreChat` class which was used to fetch fields has been removed. Pre-chat fields must be supplied by the developer directly.
- The `PreChatField.Builder.length` method has been renamed to `maxValueLength`
- The `customDetail` property in the `ChatConfiguration.Builder` class has been removed. Please use the `preChatFields` method instead.
- A `preChatEntities` property has been added to the `ChatConfiguration` builder.
- Added a new model, `ChatSentMessageReceipt`, which contains information about any Sensitive Data Rule violations found in an
outgoing chat message
- Changed the method signature of `ChatClient.sendChatMessage(String)` to return `Async<ChatSentMessageReceipt>` from `Async<Void>`
- `ChatClient.sendChatMessage(String)` will now provide a `SessionDoesNotExistException` to your `ErrorHandler` instead of a `RuntimeException`
when it is invoked without an active session.
- `ChatClient.sendChatMessage(String)` will provide a `EmptyChatMessageException` to your `ErrorHandler` if the provided string is empty, or
the string has been scrubbed clean of offending text after processing with Sensitive Data Rules. If the latter is true, the exception will contain
a list of related Sensitive Data Rules that were triggered to produce an empty string.

## Upgrade Instructions

- For customers using `ChatCore` to service their own chat UI, update your `ResultHandler` signature when invoking `ChatClient.sendChatMessage(String)`
to receive a `ChatSentMessageReceipt` instead of `Void`. You also may want to update your related `ErrorHandler` to account for `SessionDoesNotExistException`
and `EmptyChatMessageException` if that information is relevant to your use case.

## Bug Fixes

- CHAT WITH AGENT button now has background set to 30% opacity when required fields aren't filled out, and 
button cannot be pressed.
- Fixed a bug where the title text color on the Pre-Chat UI was not being set to `salesforce_toolbar_inverted`.
- Fixed a bug where the NetworkError end reason was being reported twice. End Reasons will only be reported to `onSessionEnded(ChatEndReason)` during the Ending state.

## Known Issues

- There are missing translations for strings present in `chat-ui`. Currently, only English text exists for the following strings:
	- chat_dialog_select_image_source_choose
	- chat_session_ended_by_agent
	- chat_feed_content_description
	- chat_file_transfer_requested
	- chat_file_transfer_canceled
	- chat_file_transfer_failed
	- chat_image_selection_failed
	- chat_permission_not_granted
	- pre_chat_toolbar_title
	- pre_chat_button_start
	- pre_chat_field_list_description
	- pre_chat_banner_text
	- salesforce_message_input_hint