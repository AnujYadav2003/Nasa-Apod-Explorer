package com.nasa.nasaapod.service;


import com.nasa.nasaapod.client.ApodClient;
import com.nasa.nasaapod.model.ApodResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ApodService {

    private final ApodClient apodClient;

    public ApodService(ApodClient apodClient) {
        this.apodClient = apodClient;
    }

    @Cacheable(value = "apodCache", key = "#date ?: 'today'", unless = "#result == null")
    public ApodResponse getApod(String date) {
        if (StringUtils.hasText(date)) {
            return apodClient.fetchApodByDate(date);
        } else {
            return apodClient.fetchApodToday();
        }
    }
}