package com.example.giftcard.query;

import com.example.giftcard.query.api.CardSummary;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessorConfiguration;
import org.axonframework.eventhandling.async.SequentialPerAggregatePolicy;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.axonframework.eventhandling.saga.repository.inmemory.InMemorySagaStore;
import org.axonframework.eventhandling.tokenstore.jpa.TokenEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan(basePackageClasses={TokenEntry.class, CardSummary.class})
public class GcQueryApp {

	public static void main(String[] args) {
		SpringApplication.run(GcQueryApp.class, args);
	}

	@Bean
	public SagaStore<Object> sagaStore() {
		return new InMemorySagaStore();
	}

}
