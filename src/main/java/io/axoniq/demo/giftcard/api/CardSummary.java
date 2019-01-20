package io.axoniq.demo.giftcard.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;



@Data
@AllArgsConstructor
public class CardSummary {
    @Id
    private String id;
    private int initialValue;
    private int remainingValue;
}
