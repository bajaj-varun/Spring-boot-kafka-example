package com.example.loggingconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.SubscribableChannel;

//@EnableBinding(MyEvents.class)
@SpringBootApplication
public class LoggingConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoggingConsumerApplication.class, args);
	}

	/*@StreamListener(Sink.INPUT)
	public void handle(Person person){
		System.out.println("Received: "+person.toString());
	}*/
}


/*interface MyEvents{
	String INPUT="input";

	@Input(Sink.INPUT)
	SubscribableChannel input();
}*/

