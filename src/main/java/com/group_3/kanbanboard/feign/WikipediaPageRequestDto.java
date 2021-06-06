package com.group_3.kanbanboard.feign;

import java.io.Serializable;


public class WikipediaPageRequestDto implements Serializable{

    private String base_etag;
    private String html;
    private String csrf_token;
    private String comment;
    private final boolean is_minor = true;
    private final boolean is_bot = true;

    public WikipediaPageRequestDto() {
    }

    public WikipediaPageRequestDto(String base_etag,
                                   String html,
                                   String csrf_token,
                                   String comment)                                {
        this.base_etag = base_etag;
        this.html = html;
        this.csrf_token = csrf_token;
        this.comment = comment;
    }

    public String getBase_etag() {
        return base_etag;
    }

    public void setBase_etag(String base_etag) {
        this.base_etag = base_etag;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getCsrf_token() {
        return csrf_token;
    }

    public void setCsrf_token(String csrf_token) {
        this.csrf_token = csrf_token;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isMinor() {
        return is_minor;
    }

    public boolean isBot() {
        return is_bot;
    }

}
