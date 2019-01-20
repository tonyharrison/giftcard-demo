package io.axoniq.demo.giftcard.query;
import io.axoniq.demo.giftcard.api.CardSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface CardSummaryRepository extends MongoRepository<CardSummary, String> {
    Page<CardSummary> findAllByIdStartsWith(String idStartsWith, Pageable pageable);

    Long countAllByIdStartingWith(String idStartsWith);
}
