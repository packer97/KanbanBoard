package com.group_3.kanbanboard.service.feign;

import com.group_3.kanbanboard.feign.youtube.YoutubeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class YoutubeClientService {

  private final YoutubeClient client;

  @Value("${youtube.api.key}")
  private String apiKey;

  @Autowired
  public YoutubeClientService(YoutubeClient client) {
    this.client = client;
  }

  public ResponseEntity<String> getSearchResults(String q, String maxResults) {
    return client.get25SearchResults("snippet", maxResults, q, apiKey);
  }


}
