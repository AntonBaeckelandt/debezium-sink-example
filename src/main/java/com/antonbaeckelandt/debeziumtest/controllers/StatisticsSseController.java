package com.antonbaeckelandt.debeziumtest.controllers;

import com.antonbaeckelandt.debeziumtest.statistics.messagebus.NewStatisticsMessage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class StatisticsSseController implements ApplicationListener<NewStatisticsMessage> {

    private static final Logger LOGGER = Logger.getLogger(StatisticsController.class.getSimpleName());
    private static final int HEART_BEAT_INTERVAL = 5000;

    private final List<SseEmitter> emitters = new LinkedList<>();

    public StatisticsSseController() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            while (true) {
                try {
                    sendHeartbeats();
                    Thread.sleep(HEART_BEAT_INTERVAL);
                } catch (InterruptedException e) {
                    LOGGER.log(Level.SEVERE, e.getMessage());
                }
            }
        });
    }

    @GetMapping("/statistics-sse")
    @CrossOrigin
    public SseEmitter streamStatistics() throws IOException {
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);

        emitter.onError(ex -> emitters.remove(emitter));
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }

    @Override
    public void onApplicationEvent(NewStatisticsMessage newStatistics) {
        sendSseToClients("new-statistics", newStatistics.getStatistics());
    }

    private void sendSseToClients(String name, Object message) {
        for (SseEmitter emitter : emitters) {
            try {
                SseEmitter.SseEventBuilder event = SseEmitter.event()
                        .data(message)
                        .name(name);
                emitter.send(event);
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        }
    }

    private void sendHeartbeats() {
        for (SseEmitter emitter : emitters) {
            try {
                SseEmitter.SseEventBuilder event = SseEmitter.event()
                        .data("heartbeat")
                        .name("heartbeat");
                emitter.send(event);
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        }
    }

}
