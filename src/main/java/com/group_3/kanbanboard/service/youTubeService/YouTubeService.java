package com.group_3.kanbanboard.service.youTubeService;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.group_3.kanbanboard.feign.YouTubeClient;


@Service
@JsonIgnoreProperties(value = "pageInfo")
public class YouTubeService {

    private final YouTubeClient client;

    @Value("${youtube.api.key}")
    private String apiKey;

    @Autowired
    public YouTubeService(YouTubeClient client) {
        this.client = client;
    }

    public String getResult(String id) {
        return client.getChannelInfo("snippet",id, apiKey);
    }
}
