package com.example.loggingconsumer;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@EnableBinding(AnalyticsBinding.class)
public class CustomKafkaProducer implements ApplicationRunner{

    private final MessageChannel pageViewsOut;
    private final Log log = LogFactory.getLog(getClass());

    public CustomKafkaProducer(AnalyticsBinding binding) {
        this.pageViewsOut = binding.pageViewsOut();
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        List<String> names = Arrays.asList("mfisher", "dyser", "schacko", "abilan", "ozhurakousky", "grussell");
        List<String> pages = Arrays.asList("blog", "sitemap", "initializr", "news", "colophon", "about");

        Runnable runnable = ()->{
            String rPage = pages.get(new Random().nextInt(pages.size()));
            String rName = pages.get(new Random().nextInt(names.size()));
            PageViewEvent pageViewEvent = new PageViewEvent(rName, rPage, Math.random() > .5 ? 10 : 1000);
            try {
                Message<PageViewEvent> message = MessageBuilder
                        .withPayload(pageViewEvent)
                        .setHeader(KafkaHeaders.MESSAGE_KEY, pageViewEvent.getUserId().getBytes())
                        .build();
                this.pageViewsOut.send(message);
                log.info("Sent :"+message.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(runnable,1,1, TimeUnit.SECONDS);
    }
}

interface AnalyticsBinding {

    String PAGE_VIEWS_OUT = "pvout";
    String PAGE_VIEWS_IN = "pvin";
    String PAGE_COUNT_MV = "pcmv";
    String PAGE_COUNT_OUT = "pcout";
    String PAGE_COUNT_IN = "pcin";

    // page views
    @Input(PAGE_VIEWS_IN)
    KStream<String, PageViewEvent> pageViewsIn();

    @Output(PAGE_VIEWS_OUT)
    MessageChannel pageViewsOut();

    // page cocunts
    @Output(PAGE_COUNT_OUT)
    KStream<String, Long> pageCountOut();

    @Input(PAGE_COUNT_IN)
    KTable<String, Long> pageCountIn();
}

@Data
@NoArgsConstructor
@Getter
class PageViewEvent {
    private String userId, page;
    private long duration;

    PageViewEvent(String userId, String page, long duration) {
        this.userId = userId;
        this.page = page;
        this.duration = duration;
    }

    public String getUserId() {
        return userId;
    }
}