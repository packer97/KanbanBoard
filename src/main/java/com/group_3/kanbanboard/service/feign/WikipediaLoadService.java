package com.group_3.kanbanboard.service.feign;

import java.io.IOException;

public interface WikipediaLoadService {

    String downloadHtml(String title, String htmlContent) throws IOException;

    String downloadPdf(String title, byte[] pdfContent) throws IOException;

    String downloadWikiText(String title, String wikitextContent) throws IOException;

    String uploadHtml(String path) throws IOException;}
