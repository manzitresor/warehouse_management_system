package com.thegym.warehousemanagementsystem.kafkaConfig;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic stockMovement() {
        return new NewTopic("stock-movement",2,(short) 1);
    }
}
