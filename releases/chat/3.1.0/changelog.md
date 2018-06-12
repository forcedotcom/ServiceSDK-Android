# Chat 3.1.0 

## New Features

- Adding support for Chat Bot footer menus.
- Adding support for Chat Bot quick reply buttons.
- Adding support for displaying a custom drawable asset as a Chat Bot avatar.
- Adding enhanced UX support for Chat transfers.

## API Changes

- Added Chat Bot footer menu support in Chat Core via the following APIs:
  - New Model: `ChatFooterMenuItem`
  - `ChatBotListener.onChatFooterMenuReceived(ChatFooterMenuItem[])`
  - `ChatClient.sendFooterMenuSelection(int, String)`
- Added `AgentInformation.isChatBot()` getter for identifying ChatBot agents.
- Added Chat Bot quick reply button support in Chat Core via the following APIs:
  - New Model: `ChatWindowButtonMenu`
  - `ChatBotListener.OnChatButtonMenuReceived(ChatWindowButtonMenu)`
  - `ChatClient.sendButtonSelection(int)`
- Added `ChatUIConfiguration.Builder.chatBotAvatar(int)`, which accepts a Drawable resource to be used as a Chat Bot avatar

### Pre-Chat

- Deprecated Pre-Chat classes in favor of a simplified API that de-couples the Pre-Chat user
experience from the function of passing user data to the Agent when a session starts.
  - `PreChatField` is deprecated in favor of `ChatUserData` for programmatically sending data to your
 Salesforce org, and `PreChatTextInputField` or `PreChatPickListInputField` for building a Pre-Chat
 form for your users. 
  - `PreChatEntity` is deprecated in favor of `ChatEntity`
  - `PreChatEntityField` is deprecated in favor of `ChatEntityField`
  - `ChatConfiguration.Builder.preChatFields(..)` is deprecated in favor of `ChatConfiguration.Builder.chatUserData(..)`
  - `ChatConfiguration.Builder.preChatEntities(..)` is deprecated in favor of `ChatConfiguration.Builder.chatEntities(..)`
  - `ChatConfiguration.getPreChatFields()` is deprecated in favor of `ChatConfiguration.getChatUserData()`,
  and the former will return an empty list if any of the fields specified are not of the deprecated
  `PreChatField` type in order to preserve stability.
  - `ChatConfiguration.getPreChatEntities()` is deprecated in favor of `ChatConfiguration.getChatEntities()`,
  and the former will return an empty list if any of the entities specified are not of the deprecated
  `PreChatEntity` type in order to preserve stability.
- Removing unused class `PreChatConfiguration`

## Upgrade Instructions

- If you are using `PreChatField` and related classes to pass user data through the chat session, please
migrate to using `ChatUserData`, `ChatEntity` and `ChatEntityField` instead. Please ensure that you
are providing these objects to `ChatConfiguration.Builder.chatUserData(..)` and 
`ChatConfiguration.Builder.chatEntities(..)`.

- If you are using `PreChatField` to build a Pre-chat experience for your users, please switch to
`PreChatTextInputField` or `PreChatPickListInputField` for building text input fields and pick list
fields, respectively. These classes extend `ChatUserData`, so all Pre-Chat and user data objects may
be provided to `ChatConfiguration.Builder.chatUserData(..)`.

- OkHttp 3.10.0 may require the following proguard rules in your project:
```
-dontwarn okio.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
```

