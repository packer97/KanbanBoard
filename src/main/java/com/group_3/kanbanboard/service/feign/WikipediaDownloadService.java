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

        Path downloadedHtmlDirPath = Paths.get(".", "downloaded", "html");
        if (Files.notExists(downloadedHtmlDirPath)) Files.createDirectories(downloadedHtmlDirPath);

        System.out.println(downloadedHtmlDirPath);

        Path downloadedHtmlFile = Paths.get(downloadedHtmlDirPath.toString(), title + ".html");
        if (Files.notExists(downloadedHtmlFile)) Files.createFile(downloadedHtmlFile);

        System.out.println(downloadedHtmlFile);

        return Files.write(downloadedHtmlFile, htmlContent.getBytes(), StandardOpenOption.WRITE).toString();

    }

    public String downloadPdf(String title, byte[] pdfContent) throws IOException {
        Path downloadedPdfDirPath = Paths.get(".", "downloaded", "pdf");
        if (Files.notExists(downloadedPdfDirPath)) Files.createDirectories(downloadedPdfDirPath);

        System.out.println(downloadedPdfDirPath);

        Path downloadedPdfFile = Paths.get(downloadedPdfDirPath.toString(), title + ".pdf");
        if (Files.notExists(downloadedPdfFile)) Files.createFile(downloadedPdfFile);

        System.out.println(downloadedPdfFile);

        return Files.write(downloadedPdfFile, pdfContent, StandardOpenOption.WRITE).toString();
    }
}