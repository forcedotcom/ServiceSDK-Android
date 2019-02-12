# Chat 3.3.0

## New Features
- Hyperlink preview messages now display the fav-icon image adjacent to the hostname.
- Hyperlink preview includes the device's locale in metadata requests.
- Added group conferencing support
- Added knowledge article support for hyperlink preview

## API Changes
- Expanded the signature scope of the `startChatSession` method to accept all `Activity`-inherited classes, not just ones that inherit `FragmentActivity`.
- Added a RESPONSE_SESSION_CREATED event, which is emitted when the chat session is created.
- Added a DATA_LIVE_AGENT_SESSION_ID Map Key, which retrieves the unique ID for the active chat session.
- Added `ChatEventListener` which indicates various chat events have happened. Instances of
`ChatEventListener` can be added via `ChatUIConfiguration.Builder()`.
- Added `ChatKnowledgeArticlePreviewDataHelper` callback interface for supplying a knowledge article's title and summary for generating a hyperlink preview.
- Added `ChatKnowledgeArticlePreviewDataProvider` interface for providing a knowledge article's title and summary to a hyperlink preview rendered in the chat feed.
```
public class KnowledgeArticlePreviewDataProvider implements ChatKnowledgeArticlePreviewDataProvider {
  private KnowledgeClient mKnowledgeClient;

  KnowledgeArticlePreviewDataProvider (@NonNull KnowledgeClient knowledgeClient) {
    mKnowledgeClient = knowledgeClient;
  }

  @Override
  public boolean onPreviewDataRequested(final String articleIdOrTitle, final ChatKnowledgeArticlePreviewDataHelper helper) {
    ArticleDetailRequest articleDetailRequest = ArticleDetailRequest.builder(articleIdOrTitle).build();
    mKnowledgeClient.submit(articleDetailRequest).onResult(new Async.ResultHandler<ArticleDetails>() {
      @Override
      public void handleResult(Async<?> operation, @NonNull ArticleDetails result) {
        helper.onPreviewDataReceived(result.getTitle(), result.getSummary());
      }
    }).onComplete(new Async.CompletionHandler() {
      @Override
      public void handleComplete(Async<?> operation) {
        // Handle completion
      }
    }).onError(new Async.ErrorHandler() {
      @Override
      public void handleError(Async<?> operation, @NonNull Throwable throwable) {
        // Handle error
        helper.onPreviewDataReceived(null, null);
      }
    });
    return true;
  }
}
```
- Added `ChatKnowledgeArticlePreviewDataHelper` callback interface for supplying a knowledge article's title and summary for generating a hyperlink preview.
```
public class KnowledgeArticlePreviewClickListener implements ChatKnowledgeArticlePreviewClickListener {
  private KnowledgeUIClient mKnowledgeUIClient;

  KnowledgeArticlePreviewClickListener (@NonNull KnowledgeUIClient knowledgeUIClient) {
    mKnowledgeUIClient = knowledgeUIClient;
  }

  @Override
  public boolean onClick (final Context context, final String articleIdOrTitle) {
    ArticleDetailRequest articleDetailRequest = ArticleDetailRequest.builder(articleIdOrTitle).build();
    mKnowledgeUIClient.getKnowledgeCoreClient().submit(articleDetailRequest).onResult(new Async.ResultHandler<ArticleDetails>() {
      @Override
      public void handleResult(Async<?> operation, @NonNull ArticleDetails result) {
        mKnowledgeUIClient.launchArticle((Activity) context, result);
      }
    }).onComplete(new Async.CompletionHandler() {
      @Override
      public void handleComplete(Async<?> operation) {
        // Handle completion
      }
    }).onError(new Async.ErrorHandler() {
      @Override
      public void handleError(Async<?> operation, @NonNull Throwable throwable) {
        // Handle error
      }
    });
    return true;
  }
}
```

## Customer-facing Bug Fixes
- Added the ability to select (copy) sent chat messages
- Closes previous chat minimized views when a new chat session is initiated
- Fixed text coloring of chatbot menu buttons when using TalkBack accessibility
- Initial data persists no longer destroyed when used on required pre chat fields

## Internal Bug Fixes
- Replaced End Session DialogFragment with AlertDialog
- Replaced image source selection DialogFragment with AlertDialog
- Exposed the chat session ID to logs as `uniqueId` in basicInfo

## Upgrade Instructions


## Known Issues


