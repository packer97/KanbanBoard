package com.group_3.kanbanboard.feign;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Class of accepted by response json of metadata wikipedia restAPI
 *
 *
 */

public class WikipediaResponseMetaData implements Serializable {

    private List<Map<String, Object>> items;

    public List<Map<String, Object>> getItems() {
        return items;
    }

    public void setItems(List<Map<String, Object>> items) {
        this.items = items;
    }

    public WikipediaResponseMetaData() {
    }

    public WikipediaResponseMetaData(List<Map<String, Object>> items) {
        this.items = items;
    }
}


