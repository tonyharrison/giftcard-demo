package io.axoniq.demo.giftcard.api;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class CountCardSummariesResponse {
    private final int count;
    private final long lastEvent;
}
