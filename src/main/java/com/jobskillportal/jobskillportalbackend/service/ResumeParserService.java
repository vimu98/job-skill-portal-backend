package com.jobskillportal.jobskillportalbackend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobskillportal.jobskillportalbackend.dto.ResumeUrlDTO;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class ResumeParserService {

      // Load API Key from application.properties
    private static String OPENAI_API_KEY = "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";

    public static String parseResume(ResumeUrlDTO resumeUrlDTO) throws IOException, InterruptedException, TikaException, SAXException {
        String text = extractTextFromResume(resumeUrlDTO.getResumeUrl());
        return callOpenAiApi(text);
    }

    private static InputStream downloadFile(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        return connection.getInputStream();
    }


    private static String extractTextFromResume(String fileUrl) throws IOException, TikaException, SAXException {
        // Use Apache Tika for text extraction
        InputStream fileInputStream = downloadFile(fileUrl);

        BodyContentHandler handler = new BodyContentHandler(-1); // Unlimited text length
        Metadata metadata = new Metadata();
        new AutoDetectParser().parse(fileInputStream, handler, metadata, new ParseContext());
        return handler.toString();
    }

    private static String callOpenAiApi(String resumeText) throws IOException, InterruptedException {
        // Convert text to JSON using Jackson to ensure correct format
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("model", "gpt-4o-mini");

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", "Extract skills and experience from this resume: (provide only skills separate by comma, dont provide any other unnecessary words) " + resumeText);
        messages.add(message);

        requestMap.put("messages", messages);

        // Convert Java Object to JSON string
        String requestBody = objectMapper.writeValueAsString(requestMap);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Authorization", "Bearer " + OPENAI_API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
