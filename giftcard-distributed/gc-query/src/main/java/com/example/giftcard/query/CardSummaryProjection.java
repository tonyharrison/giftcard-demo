package com.example.giftcard.query;

import com.example.giftcard.query.api.CardSummary;
import com.example.giftcard.query.api.CountCardSummariesQuery;
import com.example.giftcard.query.api.FindCardSummariesQuery;
import com.example.giftcard.command.api.IssuedEvt;
import com.example.giftcard.command.api.RedeemedEvt;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.util.Collection;

import static org.axonframework.eventhandling.GenericEventMessage.asEventMessage;

@Component
public class CardSummaryProjection {

    private final static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EntityManager entityManager;
    private final EventBus eventBus;

    public CardSummaryProjection(EntityManager entityManager, EventBus eventBus) {
        this.entityManager = entityManager;
        this.eventBus = eventBus;
    }

    @EventHandler
    public void on(IssuedEvt evt, @Timestamp Instant instant) {
        log.debug("projecting {}", evt);
        entityManager.persist(new CardSummary(evt.getId(), evt.getAmount(), instant, evt.getAmount()));
    }

    @EventHandler
    public void on(RedeemedEvt evt) {
        log.debug("projecting {}", evt);
        CardSummary summary = entityManager.find(CardSummary.class, evt.getId());
        summary.setRemainingValue(summary.getRemainingValue() - evt.getAmount());
    }

    @QueryHandler
    public Collection<CardSummary> handle(FindCardSummariesQuery query) {
        log.debug("handling {}", query);
        Query jpaQuery = entityManager.createQuery("SELECT c FROM CardSummary c ORDER BY c.id",
                CardSummary.class);
        jpaQuery.setFirstResult(query.getOffset());
        jpaQuery.setMaxResults(query.getLimit());
        Collection<CardSummary> response = jpaQuery.getResultList();
        log.debug("returning {}", response);
        return response;
    }

    @QueryHandler
    public Integer handle(CountCardSummariesQuery query) {
        log.debug("handling {}", query);
        Query jpaQuery = entityManager.createQuery("SELECT COUNT(c) FROM CardSummary c",
                Long.class);
        Integer response = ((Long)jpaQuery.getSingleResult()).intValue();
        log.debug("returning {}", response);
        return response;
    }
}
