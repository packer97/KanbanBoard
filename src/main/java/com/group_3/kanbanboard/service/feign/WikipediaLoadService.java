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
    public static final Logger logger = Logger.getLogger(WikipediaLoadService.class.getName());

    public static final String ROOT_DOWNLOAD_PATH = "downloaded";
    public static final String HTML_FILE_TYPE = "html";
    public static final String PDF_FILE_TYPE = "pdf";
    public static final String WIKITEXT_FILE_TYPE = "wiki";

    public String downloadHtml(String title, String htmlContent) throws IOException {
        return getDownload(title, htmlContent.getBytes(), HTML_FILE_TYPE);
    }

    public String downloadPdf(String title, byte[] pdfContent) throws IOException {
        return getDownload(title, pdfContent, PDF_FILE_TYPE);
    }


    public String downloadWikiText(String title, String wikitextContent) throws IOException {
        return getDownload(title, wikitextContent.getBytes(), WIKITEXT_FILE_TYPE);
    }

    public String uploadHtml(String path) throws IOException {
        Path actual = Paths.get(path);
        if (Files.exists(actual)) return new String(Files.readAllBytes(Paths.get(path)));
        else throw new RuntimeException("Page not found from existing path or not downloaded.Download it");
    }

    private String getDownload(String title, byte[] content, String fileType) throws IOException {
        Path downloadedDirPath = Paths.get(ROOT_DOWNLOAD_PATH, fileType);
        if (Files.notExists(downloadedDirPath)) Files.createDirectories(downloadedDirPath);

        Path downloadedFile = Paths.get(downloadedDirPath.toString(), title + "." + fileType);
        if (Files.notExists(downloadedFile)) Files.createFile(downloadedFile);

        logger.info(downloadedFile.toString());

        return Files.write(downloadedFile, content, StandardOpenOption.WRITE).toString();
    }
}