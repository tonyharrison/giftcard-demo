package com.example.giftcard.batch;

import io.axoniq.axonhub.client.command.CommandPriorityCalculator;
import org.axonframework.commandhandling.CommandMessage;
import org.springframework.stereotype.Component;

@Component
public class BatchPriorityCalculator implements CommandPriorityCalculator {

    @Override
    public int determinePriority(CommandMessage<?> command) {
        return -1;
    }

}
