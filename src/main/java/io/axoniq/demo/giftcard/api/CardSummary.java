package io.axoniq.demo.giftcard.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({@NamedQuery(
        query = "SELECT c FROM CardSummary c WHERE c.id LIKE CONCAT(:idStartsWith, '%') ORDER BY c.id",
        name = "CardSummary.fetch"
), @NamedQuery(
        query = "SELECT COUNT(c) FROM CardSummary c WHERE c.id LIKE CONCAT(:idStartsWith, '%')",
        name = "CardSummary.count"
)})

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardSummary {
    @Id
    private String id;
    private int initialValue;
    private int remainingValue;
}
