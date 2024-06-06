package com.vdt.fosho.config;

import com.vdt.fosho.elasticsearch.document.RestaurantDocument;
import com.vdt.fosho.service.RestaurantService;
import io.debezium.config.Configuration;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Data;

import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
@Data
public class DebeziumListener {

    private final Configuration connectorConfiguration;
    private final RestaurantService restaurantService;

    private DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Autowired
    public DebeziumListener(Configuration connectorConfiguration, RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
        this.connectorConfiguration = connectorConfiguration;

        this.debeziumEngine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
                .using(connectorConfiguration.asProperties())
                .notifying(this::handleEvent)
                .build();
    }

    private void handleEvent(RecordChangeEvent<SourceRecord> sourceRecordRecordChangeEvent) {
        SourceRecord sourceRecord = sourceRecordRecordChangeEvent.record();
        Struct value = (Struct) sourceRecord.value();

        String op = value.get("op").toString();
        Struct source = (Struct) value.get("source");
        String table = source.get("table").toString();
        Struct after = (Struct) value.get("after");

        boolean result = switch (table) {
            case "restaurant" -> handleRestaurant(after, op);
            case "orders" -> handleOrder(after, op);
            default -> false;
        };
        if (result){
            System.out.println("Replicated data successfully: " + table + ", " + op);
        } else {
            System.out.println("Failed to replicate data: " + table + ", " + op);
        }
    }

    @PostConstruct
    private void start() {
        this.executor.execute(debeziumEngine);
    }

    @PreDestroy
    private void stop() throws IOException {
        if (this.debeziumEngine != null) {
            this.debeziumEngine.close();
        }
    }

    private boolean handleRestaurant(Struct after, String op) {
        Struct coordinates = (Struct) after.get("coordinates");
        double longitude = (double) coordinates.get("x");
        double latitude = (double) coordinates.get("y");

        return restaurantService.replicateData(RestaurantDocument.builder()
                .id((Long) after.get("id"))
                .name(after.get("name").toString())
                .address(after.get("address").toString())
                .latitude(latitude)
                .longitude(longitude)
                .build(), op);
    }

     private boolean handleOrder(Struct after, String op) {
        return false;
     }
}
