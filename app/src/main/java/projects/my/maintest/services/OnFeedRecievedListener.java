package projects.my.maintest.services;

import projects.my.maintest.volley.models.Result;

/**
 * Слушатель события получения рассылки.
 */
public interface OnFeedRecievedListener {
    void OnFeedRecieved(Result data);
}
