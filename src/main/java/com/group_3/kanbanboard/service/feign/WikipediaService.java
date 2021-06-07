package com.group_3.kanbanboard.service.feign;

import com.group_3.kanbanboard.feign.WikiPediaTransformRequestDto;
import com.group_3.kanbanboard.feign.WikipediaPageRequestDto;
import com.group_3.kanbanboard.feign.WikipediaResponseMetaData;

public interface WikipediaService {

    String getHtmlPageByTitle(String title);

    byte[] getPdfPageByTitle(String title);

    WikipediaResponseMetaData getMetaDataByTitle(String title);

    void setHtmlPageByTitle(String title, WikipediaPageRequestDto request);

    String transformHtmlToWikitext(WikiPediaTransformRequestDto request);
}
