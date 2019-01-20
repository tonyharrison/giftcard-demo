package io.axoniq.demo.giftcard.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class CardSummaryFilter {
    @Getter
    private final String idStartsWith;
}
