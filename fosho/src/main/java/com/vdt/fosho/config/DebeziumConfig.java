package com.vdt.fosho.config;

import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DebeziumConfig {

    private DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine;

    @Bean
    public io.debezium.config.Configuration customerConnector() {
        return io.debezium.config.Configuration.create()
                .with("name", "custom-mariadb-connector")
                .with("connector.class", "io.debezium.connector.mysql.MySqlConnector")
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.storage.file.filename", "/tmp/offsets.dat")
                .with("offset.flush.interval.ms", 60000)
                .with("database.hostname", "mariadb")
                .with("database.port", 3306)
                .with("database.user", "root")
                .with("database.password", "password")
                .with("database.dbname", "fosho")
                .with("database.include.list", "fosho")
                .with("include.schema.changes", "false")
                .with("database.server.id", 85744)
                .with("database.server.name", "custom-mariadb-connector")
                .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
                .with("database.history.file.filename", "/tmp/dbhistory.dat")
                .build();
    }
}
