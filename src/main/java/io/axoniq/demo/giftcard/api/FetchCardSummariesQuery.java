package io.axoniq.demo.giftcard.api;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class FetchCardSummariesQuery {
    private final int offset;
    private final int limit;

    private final CardSummaryFilter filter;
}
