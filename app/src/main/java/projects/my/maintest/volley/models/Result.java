package projects.my.maintest.volley.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Модель ответа.
 */
@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class Result {
    private int totalPages;
    private List<Quote> quotes;

    public int getTotalPages() {
        return totalPages;
    }

    public List<Quote> getQuotes() {
        return new ArrayList<>(quotes);
    }

    public Quote getQuoteAt(int position) {
        if (position >= quotes.size() || position < 0) return null;
        return quotes.get(position);
    }

    public int getQuotesCount() {
        return quotes.size();
    }
}
