package io.axoniq.demo.giftcard.api;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class IssuedEvt {
    private final String id;
    private final int amount;
}
