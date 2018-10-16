# Chat 3.2.0 

## New Features

- Now targeting API 28, Android 9.0 Pie
- Adding hyperlink preview for messages sent by a support agent
- Adding an on-screen indicator for connection loss
- Improved the appearance of the "agent typing" indicator
- Added support for fullscreen chat. Applications wishing to start chat sessions in fullscreen mode
instead of the classic minimized view can configure it via `ChatUIConfiguration`:
    ```java
    new ChatUIConfiguration.Builder()
      .defaultToMinimized(false)
      ...
      .build(...);
    ```

- Added the option to hide the character counter on `PreChatTextInputField`:
    ```java
    new PreChatTextInputField.Builder()
      .counterEnabled(false)
      ...
      .build(...);
    ```

## Bug Fixes
- Minimized view unread message count will now be incremented when an agent joins and when the session ends.

