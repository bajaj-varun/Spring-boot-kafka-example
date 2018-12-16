package com.example.loggingconsumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class KafkaConsumer {
    @KafkaListener(topics = "input1", groupId = "group-id")
    public void consume(User user){
        System.out.println("Calling from Kafka COnsumer -> "+user.toString());
    }
}
