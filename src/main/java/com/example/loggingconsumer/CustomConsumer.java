package com.example.loggingconsumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
public class CustomConsumer {
    Log log = LogFactory.getLog(getClass());

    @StreamListener(target = AnalyticsBinding.PAGE_VIEWS_IN)
    public void process(KStream<String, PageViewEvent> events){
        events.foreach((k,v) -> log.info("Sink =>"+v.toString()));
    }
}
