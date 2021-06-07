package com.group_3.kanbanboard.config;

import com.group_3.kanbanboard.service.feign.WikipediaLoadService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Component
public class DownloadedDirInitializing implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {

        Path htmlDirPath = Paths.get(WikipediaLoadService.ROOT_DOWNLOAD_PATH, WikipediaLoadService.HTML_FILE_TYPE);
        if (Files.notExists(htmlDirPath)) Files.createDirectories(htmlDirPath);

        Path pdfDirPath = Paths.get(WikipediaLoadService.ROOT_DOWNLOAD_PATH, WikipediaLoadService.PDF_FILE_TYPE);
        if (Files.notExists(pdfDirPath)) Files.createDirectories(pdfDirPath);

        Path wikiDirPath = Paths.get(WikipediaLoadService.ROOT_DOWNLOAD_PATH, WikipediaLoadService.WIKITEXT_FILE_TYPE);
        if (Files.notExists(wikiDirPath)) Files.createDirectories(wikiDirPath);
    }
}
