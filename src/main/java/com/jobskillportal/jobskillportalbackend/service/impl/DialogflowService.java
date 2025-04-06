package com.jobskillportal.jobskillportalbackend.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import com.jobskillportal.jobskillportalbackend.dto.CompanyDTO;
import com.jobskillportal.jobskillportalbackend.dto.JobDTO;
import com.jobskillportal.jobskillportalbackend.service.CompanyService;
import com.jobskillportal.jobskillportalbackend.service.JobService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class DialogflowService {

    private static final String PROJECT_ID = "jobbot-yj9t"; // Replace with your Google Cloud Project ID

    private final JobService jobService;
    private final CompanyService companyService;

    public DialogflowService(JobService jobService, CompanyService companyService) {
        this.jobService = jobService;
        this.companyService = companyService;
    }

    public String detectIntent(String sessionId, String message, Long jobId) {
        try {
            // Load credentials
            ClassPathResource resource = new ClassPathResource("chatbot-key.json");
            InputStream inputStream = resource.getInputStream();
            GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);

            // Create SessionsClient
            SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
            SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(() -> credentials).build();
            SessionsClient sessionsClient = SessionsClient.create(sessionsSettings);

            // Create session path
            SessionName session = SessionName.of(PROJECT_ID, sessionId);

            // Build the query input
            TextInput.Builder textInput = TextInput.newBuilder()
                    .setText(message)
                    .setLanguageCode("en-US");
            QueryInput queryInput = QueryInput.newBuilder()
                    .setText(textInput)
                    .build();

            // Prepare parameters map
            Map<String, String> parameters = new HashMap<>();

            // If jobId is provided, fetch job and company details
            if (jobId != null) {
                JobDTO job = jobService.getJobById(jobId);
                if (job != null) {
                    parameters.put("job_title", job.getTitle());
                    parameters.put("skills_required", job.getSkillsRequired());
                    parameters.put("location", job.getLocation());
                    parameters.put("salary", job.getSalary());
                    // Add other job fields as needed

                    // Get company details
                    CompanyDTO company = companyService.getCompanyById(job.getCompanyId()).orElse(null);
                    if (company != null) {
                        parameters.put("company", company.getName());
                        // Add other company fields as needed
                    }
                }
            }

            // Build parameters
            QueryParameters.Builder queryParams = QueryParameters.newBuilder();
            Struct.Builder structBuilder = Struct.newBuilder();

            // Add all parameters to the context
            parameters.forEach((key, value) -> {
                structBuilder.putFields(key, Value.newBuilder().setStringValue(value).build());
            });

            queryParams.setPayload(structBuilder.build());

            // Build the request
            DetectIntentRequest request = DetectIntentRequest.newBuilder()
                    .setSession(session.toString())
                    .setQueryInput(queryInput)
                    .setQueryParams(queryParams.build())
                    .build();

            // Execute the request
            DetectIntentResponse response = sessionsClient.detectIntent(request);
            String fulfillmentText = response.getQueryResult().getFulfillmentText();

            // Replace placeholders with actual values
            fulfillmentText = replacePlaceholders(fulfillmentText, parameters);

            return fulfillmentText;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error communicating with Dialogflow: " + e.getMessage();
        }
    }

    private String replacePlaceholders(String text, Map<String, String> parameters) {
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            // Replace ${parameter} format
            String placeholder = "${" + entry.getKey() + "}";
            text = text.replace(placeholder, entry.getValue());
        }
        return text;
    }
}