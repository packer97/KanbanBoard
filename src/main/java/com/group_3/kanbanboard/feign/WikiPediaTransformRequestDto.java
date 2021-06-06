package com.group_3.kanbanboard.feign;

import java.io.Serializable;

public class WikiPediaTransformRequestDto implements Serializable {

    private String html;
    private final boolean scrub_wikitext = true;

    public WikiPediaTransformRequestDto(String html) {
        this.html = html;
    }

    public WikiPediaTransformRequestDto() {
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public boolean isScrub_wikitext() {
        return scrub_wikitext;
    }
}
