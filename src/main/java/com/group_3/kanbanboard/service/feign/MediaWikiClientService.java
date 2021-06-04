package com.group_3.kanbanboard.service.feign;

import com.group_3.kanbanboard.feign.mediawiki.MediaWikiClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MediaWikiClientService {

  private final MediaWikiClient client;

  @Autowired
  public MediaWikiClientService(MediaWikiClient client) {
    this.client = client;
  }

  public boolean createNewAccount(String username, String password, String retype) throws ParseException {

    String token = getCreateAccountToken();
    String finalResponse = client.createAccount("createaccount", username, password, retype, "https://localhost:8080/mediawiki/register", token, "json").getBody();

    return true;
  }

  public String getCreateAccountToken() throws ParseException {
    String response = client.getTokenForCreatingAccounts("query", "tokens", "createaccount", "json").getBody();
    JSONObject jo = (JSONObject) new JSONParser().parse(response);
    JSONObject query = (JSONObject) jo.get("query");
    JSONObject tokens = (JSONObject) query.get("tokens");
    return tokens.get("createaccounttoken").toString();
  }

}

