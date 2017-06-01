# Chat <version>

## New Features

- Now preventing multiple ChatClients from being instantiated. An `IllegalStateException` will
be passed to your `Async.ErrorHandler` if a chat session is already running. [W-3540098]
- Now allowing users to maximize the chat feed after a session has ended so they can review the content of the chat session
- Displaying a heads-up notification when a message is received from the Agent and the App is backgrounded
- Added an API to set the Visitor Name in the ChatConfiguration object.
- Now supports File Transfer. An Agent may request a file upload from the chat visitor.
- Added a `SessionInfoListener` to `chat-core` in order to expose the unique Visitor ID.
- Adding support for SneakPeek 
- Added a default PreChat UI which a developer can leverage to allow users to fill in important fields.
- The default UI experience, `chat-ui`, now has multi-linugal support for 26 languages.
- Added support for programmatically ending the session when using `chat-ui`.
- Added support for querying the availability of an agent at any time. [W-3432776](https://gus.my.salesforce.com/apex/adm_userstorydetail?id=a07B0000002SCKWIA4&sfdc.override=1)
- Accessibility improvements
- Added support for querying the current LiveAgentPod which will service your chat requests. This is part of the Availability API. [W-3717530](https://gus.my.salesforce.com/apex/adm_userstorydetail?id=a07B0000002pcTwIAI&sfdc.override=1)

#### Availability & LiveAgentPod Update Example

```java
String organizationId = "111112222233333"
String deploymentId = "333332222211111"
String buttonId = "444445555566666"
String liveAgentPod = "d.test.salesforce.com"

final ChatConfiguration chatConfiguration = new ChatConfiguration.Builder(organizationId, buttonId, deploymentId, liveAgentPod).build();
AgentAvailabilityClient client = ChatCore.configureAgentAvailability(chatConfiguration);

client.check().onResult(new Async.ResultHandler<AvailabilityState>() {
  @Override
  public void handleResult (Async<?> async, @NonNull AvailabilityState state) {
    String checkStatus = "";
    switch (state.getStatus()) {
      case AgentsAvailable: {
        checkStatus = "Agents are available";
        break;
      }
      case NoAgentsAvailable: {
        checkStatus = "No Agents available";
        break;
      }
      case Unknown: {
        checkStatus = "Unknown";
        break;
      }
    }

    mAgentAvailabilityStatus.setText(checkStatus);
    Toast.makeText(getContext().getApplicationContext(), "Live Agent Pod: " + state.getLiveAgentPod(), Toast.LENGTH_LONG).show();
});
```

## API Changes
- Added PreChat and PreChatConfiguration classes to allow a developer to define PreChatFields to be used to collect information from
end users to pass to the agent. (https://gus.my.salesforce.com/apex/ADM_WorkView?id=a07B0000002sNPIIA2&sfdc.override=1)[W-3681676]

- Added a `PreChatUIListener` interface so you can track whether the user has successfully submitted the PreChat form or has closed it without completing.
   - `ChatUIClient.addPreChatUIListener(PreChatUIListener)`
   - `ChatUIClient.removePreChatUIListener(PreChatUIListener)`

- Adding support in `chat-core` for File Transfer by adding the following APIs:
    - New Interface: `FileTransferRequestListener`
    - New Enumeration: `FileTransferStatus`
    - `ChatClient.addFileTransferRequestListener(FileTransferRequestListener)`
    - `ChatClient.removeFileTransferRequestListener(FileTransferRequestListener)`

- Changed `ChatUIClient.startChatSession` return signature from void to `Async<Void>`.
This allows consumers to attach handlers for complete/error. And allows us to prevent multiple chat sessions from initializing if they are started from the default `ChatUIClient` and report that status to the application developer.

- When a Chat Agent requests a File Transfer, an instance of `FileTransferAssistant` will be passed to your implementation of `FileTransferRequestListener.onFileTransferRequest(...). You may pass a byte array and mime type to `FileTransferAssistant.uploadFile(byte[], String)` to commence uploading a file. The file will be attached to the support case as selected by the Agent.

- Adding support in `chat-core` for obtaining the unique Visitor ID by adding the following APIs:
    - New model: `ChatSessionInfo`
    - New Interface: `SessionInfoListener`
    - `ChatClient.addSessionInfoListener(SessionInfoListener)`
    - `ChatClient.removeSessionInfoListener(SessionInfoListener)`

- Adding support in `chat-core` for sending SneakPeek messages to the Agent. This feature is intended to be used as an alternative to
`ChatClient.setIsUserTyping(boolean)` when the Agent belongs to a Live Agent Configuration that has sneak peek enabled.
    - `AgentInformation.isSneakPeekEnabled()`
    - `ChatClient.sendSneakPeekMessage(String)`

- Adding `ChatUIClient.endChatSession()`

## Upgrade Instructions

## Bug Fixes

- Fixing a rare crash that would occur if the session was ended during the Connecting state. [W-3801090]

## Notes

## Known Issues

