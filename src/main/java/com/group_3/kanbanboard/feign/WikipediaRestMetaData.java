package com.group_3.kanbanboard.feign;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class WikipediaRestMetaData implements Serializable {

    private List<Map<String, Object>> items;

    public List<Map<String, Object>> getItems() {
        return items;
    }

    public void setItems(List<Map<String, Object>> items) {
        this.items = items;
    }

    public WikipediaRestMetaData() {
    }

    public WikipediaRestMetaData(List<Map<String, Object>> items) {
        this.items = items;
    }
}


