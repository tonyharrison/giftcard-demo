package com.example.giftcard.batch;

import com.example.giftcard.batch.api.BatchSummary;
import com.example.giftcard.command.api.IssueCmd;
import org.axonframework.commandhandling.gateway.CommandGateway;

import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class BatchJob {

    private static final Executor resultExecutor = Executors.newFixedThreadPool(16);
    private final CommandGateway commandGateway;
    private final UUID id;
    private final int nRequested;
    private final AtomicInteger nSucceeded = new AtomicInteger();
    private final AtomicInteger nFailed = new AtomicInteger();

    public BatchJob(CommandGateway commandGateway, UUID id, int nRequested) {
        this.commandGateway = commandGateway;
        this.id = id;
        this.nRequested = nRequested;
        new Thread(() -> this.run()).start();
    }

    public void run() {
        for(int i = 0; i < nRequested; i++) {
            String id = "B" + UUID.randomUUID().toString().toUpperCase().substring(0, 16);
            commandGateway.send(new IssueCmd(id, 100))
                    .handleAsync((result, throwable) -> handle(result, throwable), resultExecutor);
        }
    }

    public Object handle(Object result, Throwable throwable) {
        if(throwable == null) {
            nSucceeded.incrementAndGet();
        } else {
            nFailed.incrementAndGet();
        }
        return result;
    }

    public BatchSummary getSummary() {
        BatchSummary summary = new BatchSummary();
        summary.setId(id);
        summary.setNRequested(nRequested);
        summary.setNSucceeded(nSucceeded.get());
        summary.setNFailed(nFailed.get());
        return summary;
    }

}
