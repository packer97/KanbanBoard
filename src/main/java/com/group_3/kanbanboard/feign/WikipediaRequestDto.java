package com.group_3.kanbanboard.feign;

public class WikipediaRequestDto {

    private String base_etag;
    private String html;
    private String csrf_token;
    private String comment;
    private boolean minor;
    private boolean bot;

    public WikipediaRequestDto() {
    }

    public WikipediaRequestDto(String base_etag,
                               String html,
                               String csrf_token,
                               String comment,
                               boolean minor,
                               boolean bot) {
        this.base_etag = base_etag;
        this.html = html;
        this.csrf_token = csrf_token;
        this.comment = comment;
        this.minor = minor;
        this.bot = bot;
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
        return minor;
    }

    public void setMinor(boolean minor) {
        this.minor = minor;
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }
}
