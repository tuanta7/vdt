package com.vdt.fosho.config;

import com.vdt.fosho.service.RestaurantService;
import io.debezium.config.Configuration;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Data;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Data
public class DebeziumListener {

    private final Configuration customerConnectorConfiguration;
    private final RestaurantService restaurantService;
    private DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Autowired
    public DebeziumListener(Configuration customerConnectorConfiguration, RestaurantService restaurantService) {
        this.customerConnectorConfiguration = customerConnectorConfiguration;
        this.restaurantService = restaurantService;

        this.debeziumEngine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
                .using(customerConnectorConfiguration.asProperties())
                .notifying(this::handleEvent)
                .build();
    }

    private void handleEvent(RecordChangeEvent<SourceRecord> sourceRecordRecordChangeEvent) {
        System.out.println("Received event: " + sourceRecordRecordChangeEvent);
    }

    @PostConstruct
    private void start() {
        executorService.submit(() -> {
            try {
                debeziumEngine.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @PreDestroy
    private void stop() {
        if (debeziumEngine != null) {
            try {
                debeziumEngine.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }
}
