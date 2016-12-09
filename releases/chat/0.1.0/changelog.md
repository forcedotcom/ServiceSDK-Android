# Chat 0.1.0 - Pilot Release

## Features

- Support for creating LiveAgent Chat sessions from an Android application with
basic chat functionality.
- Chat feed minimization. The user can maintain an active chat session and
browse the application simultaneously with a non-blocking UI.
- The `chat-core` module offers an API that supports creating LiveAgent Chat
sessions without a pre-packaged UI, allowing customers to create their own.
- The `chat-ui` module conforms with the Service SDK branding token scheme to
enable color customization of the pre-packaged Chat UI.

## Quick-Start Integration

- Minimum supported Android API: 16, Jelly Bean
- For integration with the pre-packaged Chat UI, add the following configuration
to your project's Gradle file:

```
repositories {
  maven { url 'https://salesforcesos.com/android/maven/release' }
}

dependencies {
  compile 'com.salesforce.service:chat-ui:0.1.0'
}
```

- To create a LiveAgent Chat session from your application you must first
configure LiveAgent in your Salesforce Organization and obtain the following
information:
  - Salesforce Organization ID
  - A LiveAgent Button ID
  - A LiveAgent Deployment ID
  - LiveAgent Pod name (Note: Only use the FQDN, not the entire LiveAgent URL)

- Once you have obtained your Organization's LiveAgent information, you must
create a new `ChatConfiguration` object followed by a `ChatUIConfiguration`
 object:

```
ChatConfiguration chatConfiguration =
  new ChatConfiguration.Builder(ORG_ID, BUTTON_ID,
                                DEPLOYMENT_ID, LIVE_AGENT_POD)
                                .build();
ChatUIConfiguration chatUIConfiguration = ChatUIConfiguration.create(chatConfiguration);
```

- Pass the `ChatUIConfiguration` to `ChatUI.configure(..)`, call
`.createClient(Context)` and handle the `ChatUIClient` result asynchronously.
You may then initiate a new Chat session by calling
`ChatUIClient.startSession(FragmentActivity)`:

```
ChatUI.configure(chatUIConfiguration)
  .createClient(getApplicationContext())
  .onResult(new Async.ResultHandler<ChatUIClient>() {
      @Override public void handleResult (Async<?> operation,
        @NonNull ChatUIClient chatUIClient) {
          // Call startChatSession(..) on the chatUIClient to start a session and launch the UI.
          chatUIClient.startChatSession((FragmentActivity) activity);
      }
});
```

## Integration with Chat Core (Headless API)

- For integration *without* the pre-packaged UI, depend on `chat-core:0.1.0`
instead. You may then start new headless sessions by invoking `ChatCore`:

```
ChatConfiguration chatConfiguration =
  new ChatConfiguration.Builder(ORG_ID, BUTTON_ID,
                                DEPLOYMENT_ID, LIVE_AGENT_POD)
                                .build();
ChatCore.configure(chatConfiguration).createClient(Context)
  .onResult(new Async.ResultHandler<ChatClient>() {
      @Override public void handleResult (Async<?> operation, @NonNull ChatClient chatClient) {
        // The session is now active and attempting to connect.
        // Control and listen to the session via the chatClient instance.
      }
    });
```

- To listen for Chat session state events, implement the `SessionStateListener`
interface and register it with the `ChatClient`:

```
chatClient.addSessionStateListener(SessionStateListener)
```

- To listen for information about the Agent and to receive messages, implement
the `AgentListener` interface and register it with the `ChatClient`:

```
chatClient.addAgentListener(AgentListener);
```

## Known Limitations and Issues

- Not currently compatible in the same Android application as SOS and Knowledge
- Languages other than English are not yet supported
- Default Pre-chat UI form is not present in the Chat Pilot
- File transfer is not yet supported
- Text in the chat feed is not selectable for copying
- Hyperlinks present in the chat feed are not parsed
- Message retries are not currently supported
- Checking for Agent Availability before a Chat session is initiated is not yet
supported
- Closing the UI from the minimized chat UI after the session has already ended
by the agent will result in an erroneous confirmation dialog asking the user if
they are sure they want to end the session
- The minimized view color contrast is not optimal with a dark-themed color
branding configuration
