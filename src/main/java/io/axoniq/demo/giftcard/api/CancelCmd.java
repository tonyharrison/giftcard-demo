package io.axoniq.demo.giftcard.api;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CancelCmd {
    @TargetAggregateIdentifier
    private final String id;
}
