package io.axoniq.demo.giftcard.api;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


@Value
public class RedeemCmd {

    @TargetAggregateIdentifier
    private final String id;
    private final int amount;
}
