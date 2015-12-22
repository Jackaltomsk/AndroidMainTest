package projects.my.maintest.services;

/**
 * Контракт сервиса получения рассылок.
 */
public interface FeedServiceContract {
    void setFeedRecievedListener(OnFeedRecievedListener feedRecievedListener);
    boolean isInProgess();
    void requestFeed();
}
