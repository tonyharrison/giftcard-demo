package io.axoniq.demo.giftcard.api;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor

public class CountCardSummariesQuery {
    @Getter
    private final CardSummaryFilter filter;

    public CountCardSummariesQuery(){
        this.filter = new CardSummaryFilter("");
    }
    public String toString() {
        return "CountCardSummariesQuery";
    }
}
