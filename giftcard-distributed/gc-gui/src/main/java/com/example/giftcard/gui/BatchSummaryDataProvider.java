package com.example.giftcard.gui;

import com.example.giftcard.batch.api.BatchSummary;
import com.example.giftcard.batch.api.CountBatchSummariesQuery;
import com.example.giftcard.batch.api.FindBatchSummariesQuery;
import com.vaadin.data.provider.CallbackDataProvider;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.responsetypes.ResponseTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class BatchSummaryDataProvider extends CallbackDataProvider<BatchSummary, Void> {

    private final static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final ScheduledFuture<?> updateTask;

    public BatchSummaryDataProvider(QueryGateway queryGateway) {
        super(
                q -> {
                    FindBatchSummariesQuery query = new FindBatchSummariesQuery(q.getOffset(), q.getLimit());
                    Collection<BatchSummary> response = queryGateway.query(
                            query, ResponseTypes.multipleInstancesOf(BatchSummary.class)).join();
                    return response.stream();
                },
                q -> {
                    return queryGateway.query(new CountBatchSummariesQuery(),
                            ResponseTypes.instanceOf(Integer.class)).join();
                }
        );
        updateTask = executor.scheduleAtFixedRate(this::refreshAll, 500, 500, TimeUnit.MILLISECONDS);
    }

    public void shutDown() {
        updateTask.cancel(true);
    }

}
