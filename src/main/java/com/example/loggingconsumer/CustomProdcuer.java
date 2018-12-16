package com.example.loggingconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafka")
public class CustomProdcuer {

    @Autowired
    KafkaTemplate<String, User> kafkaTemplate;

    @GetMapping("/publish/{message}")
    public String post(@PathVariable (name = "message") final String message){
        kafkaTemplate.send("input1", new User(message, "Information technology", 12000L));
        return "Published successfully";
    }
}
