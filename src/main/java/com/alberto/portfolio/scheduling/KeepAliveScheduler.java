package com.alberto.portfolio.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KeepAliveScheduler {

    private static final Logger log = LoggerFactory.getLogger(KeepAliveScheduler.class);

    private final RestTemplate http = new RestTemplate();

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    // Run every 14 minutes (Render free tier idles at ~15min)
    @Scheduled(fixedRateString = "${app.keepalive.rate-ms:840000}")
    public void ping() {
        try {
            String url = baseUrl + "/health";
            ResponseEntity<String> resp = http.getForEntity(url, String.class);
            log.info("KeepAlive ping {} -> {}", url, resp.getStatusCode());
        } catch (Exception e) {
            log.warn("KeepAlive ping failed: {}", e.getMessage());
        }
    }
}
