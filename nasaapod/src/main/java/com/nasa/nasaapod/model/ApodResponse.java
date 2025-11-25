package com.nasa.nasaapod.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApodResponse {
    private String date;
    private String title;
    private String explanation;
    private String url;
    private String hdurl;
    private String mediaType; // "image" or "video"
    private String serviceVersion;
    private String copyright;

    @JsonProperty("media_type")
    public void setMediaType(String mediaType) { this.mediaType = mediaType; }
}
