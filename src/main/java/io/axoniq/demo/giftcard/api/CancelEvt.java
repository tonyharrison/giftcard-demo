package io.axoniq.demo.giftcard.api;

import lombok.Value;

@Value
public class CancelEvt {
    private final String id;
}
