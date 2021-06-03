package com.group_3.kanbanboard.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.api.services.youtube.model.SearchListResponse;
import com.group_3.kanbanboard.feign.youtube.YoutubeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@JsonIgnoreProperties(value = "pageInfo")
public class YoutubeClientService {

  private final YoutubeClient client;

  @Value("${youtube.api.key}")
  private String apiKey;

  @Autowired
  public YoutubeClientService(YoutubeClient client) {
    this.client = client;
  }

  public String getSearchResults(String q, String maxResults) {
    return client.get25SearchResults("snippet", maxResults, q, apiKey);
  }


}
