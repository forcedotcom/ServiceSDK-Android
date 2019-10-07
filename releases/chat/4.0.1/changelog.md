# Chat 4.0.1

## New Features
 - Added an option to display estimated wait times instead of queue positions.
 - Added API for listening to the `onChatTransferred` event.
 - Added new UI elements to better convey that the session is in a reconnecting state.

## API Changes
 - `QueueListener.onQueueEstimatedWaitTimeUpdate (int estimatedWaitTime, int queuePosition)` - Provides the estimated amount of time in seconds before a visitor is assigned an agent along with their current position in the queue.
 - `ChatUIConfiguration.maximumWaitTime(int maximumWaitTime)` - Provides the maximum wait time in minutes to be displayed to the user.
 - `ChatUIConfiguration.minimumWaitTime(int minimumWaitTime)` - Provides the minimum wait time in minutes to be displayed to the user.
 - `ChatUIConfiguration.queueStyle(QueueStyle queueStyle)` -  Specifies the way queue updates are conveyed to the user while waiting in a queue. QueueStyle Can be one of `None`, `Position`, or `EstimatedWaitTime`.
 - `AgentListener.onChatTransferred (AgentInformation agentInformation)` - Provides the new `agentInformation` when a chat transfer occurs. Handles the cases where a chatbot transfers to an agent or an agent transfers to another agent.

## Customer-facing Bug Fixes
 - Announces typing indicator for accessibility
 - Announces reconnect bar text for accessibility
 - Fixed an issue where an empty chat feed could be displayed early

## Internal Bug Fixes
 - [W-6325121](https://gus.lightning.force.com/lightning/r/ADM_Work__c/a07B0000007ImRsIAK/view) Fixed issue that prevented analytics logs from being sent due to invalid file transfer schema values.
 - Added a new way to identify bots vs agents since bots will not always have a footer menu.

## Upgrade Instructions
 - A new public interface was added to `AgentListener`. If this interface was already being implemented you must also implement `AgentListener.onChatTransferred (AgentInformation agentInformation)`.
 - A new public interface was added to `QueueListener`. If this interface was already being implemented you must also implement `QueueListener.onQueueEstimatedWaitTimeUpdate (int estimatedWaitTime, int queuePosition)`.

## Known Issues
