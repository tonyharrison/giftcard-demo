package com.example.giftcard.batch;

import com.example.giftcard.batch.api.BatchSummary;
import com.example.giftcard.batch.api.CountBatchSummariesQuery;
import com.example.giftcard.batch.api.FindBatchSummariesQuery;
import com.example.giftcard.batch.api.StartBatchCmd;
import com.example.giftcard.command.api.IssuedEvt;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Component
public class BatchManager {

    private final CommandGateway commandGateway;
    private final ConcurrentHashMap<UUID, BatchJob> jobsMap = new ConcurrentHashMap<>();
    private final CopyOnWriteArrayList<BatchJob> jobsList = new CopyOnWriteArrayList<>();

    public BatchManager(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @CommandHandler
    public void handle(StartBatchCmd cmd) {
        BatchJob newJob = new BatchJob(commandGateway, cmd.getId(), cmd.getNumber());
        jobsMap.put(cmd.getId(), newJob);
        jobsList.add(newJob);
    }

    @QueryHandler
    public List<BatchSummary> handle(FindBatchSummariesQuery query) {
        return jobsList
                .subList(
                        Math.min(jobsList.size(), query.getOffset()),
                        Math.min(jobsList.size(), query.getOffset() + query.getLimit()))
                .stream()
                .map(BatchJob::getSummary)
                .collect(Collectors.toList());
    }

    @QueryHandler
    public Integer handle(CountBatchSummariesQuery query) {
        return jobsList.size();
    }

}
