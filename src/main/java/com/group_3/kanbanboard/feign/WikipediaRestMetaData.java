package com.group_3.kanbanboard.feign;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class WikipediaRestMetaData implements Serializable {

    private List<MetaData> items;

    public WikipediaRestMetaData() {
    }

    public WikipediaRestMetaData(List<MetaData> items) {
        this.items = items;
    }

    public List<MetaData> getItems() {
        return items;
    }

    public void setItems(List<MetaData> items) {
        this.items = items;
    }

    public static class MetaData implements Serializable {
        boolean redirect;
        private String title;
        private long page_id;
        private long rev;
        private UUID tid;
        private int namespace;
        private long user_id;
        private String user_text;
        private LocalDateTime timestamp;
        private String comment;
        private String[] tags;
        private String[] restrictions;
        private String page_language;

        public MetaData() {
        }

        @Override
        public String toString() {
            return "MetaData{" +
                    "redirect=" + redirect +
                    ", title='" + title + '\'' +
                    ", page_id=" + page_id +
                    ", rev=" + rev +
                    ", tid=" + tid +
                    ", namespace=" + namespace +
                    ", user_id=" + user_id +
                    ", user_text='" + user_text + '\'' +
                    ", timestamp=" + timestamp +
                    ", comment='" + comment + '\'' +
                    ", tags=" + Arrays.toString(tags) +
                    ", restrictions=" + Arrays.toString(restrictions) +
                    ", page_language='" + page_language + '\'' +
                    '}';
        }
    }
}

