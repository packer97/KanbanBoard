package com.group_3.kanbanboard.config;

import com.group_3.kanbanboard.service.feign.WikipediaLoadServiceImpl;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



public class DownloadedDirInitializingBean {
    @PostConstruct
    public void init() throws Exception {

        Path htmlDirPath = Paths.get(WikipediaLoadServiceImpl.ROOT_DOWNLOAD_PATH, WikipediaLoadServiceImpl.HTML_FILE_TYPE);
        if (Files.notExists(htmlDirPath)) Files.createDirectories(htmlDirPath);

        Path pdfDirPath = Paths.get(WikipediaLoadServiceImpl.ROOT_DOWNLOAD_PATH, WikipediaLoadServiceImpl.PDF_FILE_TYPE);
        if (Files.notExists(pdfDirPath)) Files.createDirectories(pdfDirPath);

        Path wikiDirPath = Paths.get(WikipediaLoadServiceImpl.ROOT_DOWNLOAD_PATH, WikipediaLoadServiceImpl.WIKITEXT_FILE_TYPE);
        if (Files.notExists(wikiDirPath)) Files.createDirectories(wikiDirPath);
    }
}
