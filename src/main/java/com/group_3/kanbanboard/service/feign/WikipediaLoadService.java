package com.group_3.kanbanboard.service.feign;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Logger;

@Service
public class WikipediaLoadService {

    private static final Logger logger = Logger.getLogger(WikipediaLoadService.class.getName());

    public String downloadHtml(String title, String htmlContent) throws IOException {

        Path downloadedHtmlDirPath = Paths.get(".", "downloaded", "html");
        if (Files.notExists(downloadedHtmlDirPath)) Files.createDirectories(downloadedHtmlDirPath);


        Path downloadedHtmlFile = Paths.get(downloadedHtmlDirPath.toString(), title + ".html");
        if (Files.notExists(downloadedHtmlFile)) Files.createFile(downloadedHtmlFile);

       logger.info(downloadedHtmlFile.toString());

        return Files.write(downloadedHtmlFile, htmlContent.getBytes(), StandardOpenOption.WRITE).toString();

    }

    public String downloadPdf(String title, byte[] pdfContent) throws IOException {

        Path downloadedPdfDirPath = Paths.get(".", "downloaded", "pdf");
        if (Files.notExists(downloadedPdfDirPath)) Files.createDirectories(downloadedPdfDirPath);

        Path downloadedPdfFile = Paths.get(downloadedPdfDirPath.toString(), title + ".pdf");
        if (Files.notExists(downloadedPdfFile)) Files.createFile(downloadedPdfFile);

        logger.info(downloadedPdfFile.toString());

        return Files.write(downloadedPdfFile, pdfContent, StandardOpenOption.WRITE).toString();
    }

    public String uploadHtml(String path) throws IOException {
        Path actual = Paths.get(path);
        if (Files.exists(actual)) return new String(Files.readAllBytes(Paths.get(path)));
        else throw new RuntimeException("Page not found from existing path or not downloaded.Download it");
    }
}