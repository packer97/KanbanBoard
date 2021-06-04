package com.group_3.kanbanboard.service.feign;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
public class WikipediaDownloadService {

    public String downloadHtml(String title, String htmlContent) throws IOException {

        Path downloadedDirPath = Paths.get(".\\downloaded");
        if (Files.notExists(downloadedDirPath)) Files.createDirectories(downloadedDirPath);

        Path downloadedHtmlFile = Paths.get(downloadedDirPath.toString(), title + ".html");
        if(Files.notExists(downloadedHtmlFile)) Files.createFile(downloadedHtmlFile);


        return Files.write(downloadedHtmlFile, htmlContent.getBytes(), StandardOpenOption.WRITE).toString();
    }
}
