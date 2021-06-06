package com.group_3.kanbanboard.service;

import com.group_3.kanbanboard.feign.WikiClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WikiService {

    private final WikiClient wikiClient;

    @Autowired
    public WikiService(WikiClient wikiClient) {
        this.wikiClient = wikiClient;
    }

    public String getPage(String action, String list, String srsearch) {
        return wikiClient.getPage(action, list, srsearch,"json").getBody();
    }

    public String getToken() {
        String body = wikiClient.getToken("query", "tokens", "login","json").getBody();
        JSONObject jsonObject = new JSONObject(body);
        return jsonObject.getJSONObject("query").getJSONObject("tokens").getString("logintoken");
    }

    public boolean login(String username, String password) {
        wikiClient.login("clientlogin", username, password, "https://localhost:8080/wiki", getToken(), "json");
        return true;
    }
}
