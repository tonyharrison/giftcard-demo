package com.example.giftcard.gui;

import com.example.giftcard.query.api.*;
import com.vaadin.data.provider.CallbackDataProvider;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.responsetypes.ResponseTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.List;

public class CardSummaryDataProvider extends CallbackDataProvider<CardSummary, Void> {

    private final static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
    }

}
