package com.example.giftcard.batch;

import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.axonframework.eventhandling.saga.repository.inmemory.InMemorySagaStore;
import org.axonframework.eventhandling.tokenstore.jpa.TokenEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan(basePackageClasses={TokenEntry.class})
public class GcBatchApp {

	public static void main(String[] args) {
		SpringApplication.run(GcBatchApp.class, args);
	}

	@Bean
	public SagaStore<Object> sagaStore() {
		return new InMemorySagaStore();
	}
	
	@Autowired
	public void configure(EventHandlingConfiguration config) {
		config.usingTrackingProcessors();
	}
}
