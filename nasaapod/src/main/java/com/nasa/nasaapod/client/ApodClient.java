package com.nasa.nasaapod.client;

import com.nasa.nasaapod.model.ApodResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
public class ApodClient {

    private final WebClient webClient;
    private final String apiKey;

    public ApodClient(WebClient.Builder builder, @Value("${apod.base-url}") String baseUrl,
                      @Value("${NASA_API_KEY:}") String apiKeyFromEnv) {
        this.webClient = builder.baseUrl(baseUrl).build();
        this.apiKey = apiKeyFromEnv;
    }

    public ApodResponse fetchApodByDate(String date) {
        try {
            Mono<ApodResponse> mono = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("api_key", apiKey)
                            .queryParam("date", date)
                            .build())
                    .retrieve()
                    .onRawStatus(status -> status >= 400, clientResponse -> Mono.error(new RuntimeException("NASA API error")))
                    .bodyToMono(ApodResponse.class);

            return mono.block();
        } catch (WebClientResponseException ex) {
            throw new RuntimeException("Failed to fetch APOD: " + ex.getResponseBodyAsString(), ex);
        }
    }

    public ApodResponse fetchApodToday() {
        try {
            Mono<ApodResponse> mono = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("api_key", apiKey)
                            .build())
                    .retrieve()
                    .bodyToMono(ApodResponse.class);
            return mono.block();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to fetch APOD today", ex);
        }
    }
}