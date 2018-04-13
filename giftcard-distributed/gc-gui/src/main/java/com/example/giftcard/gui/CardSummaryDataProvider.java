package com.example.giftcard.gui;

import com.example.giftcard.query.api.CardSummary;
import com.example.giftcard.query.api.CountCardSummariesQuery;
import com.example.giftcard.query.api.FindCardSummariesQuery;
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

public class CardSummaryDataProvider extends CallbackDataProvider<CardSummary, Void> {

    private final static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final ScheduledFuture<?> updateTask;

    public CardSummaryDataProvider(QueryGateway queryGateway) {
        super(
                q -> {
                    FindCardSummariesQuery query = new FindCardSummariesQuery(q.getOffset(), q.getLimit());
                    Collection<CardSummary> response = queryGateway.query(
                            query, ResponseTypes.multipleInstancesOf(CardSummary.class)).join();
                    return response.stream();
                },
                q -> {
                    return queryGateway.query(new CountCardSummariesQuery(),
                            ResponseTypes.instanceOf(Integer.class)).join();
                }
        );
        updateTask = executor.scheduleAtFixedRate(this::refreshAll, 500, 500, TimeUnit.MILLISECONDS);
    }

    public void shutDown() {
        updateTask.cancel(true);
    }

}
