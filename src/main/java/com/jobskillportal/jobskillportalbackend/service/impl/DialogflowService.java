package com.jobskillportal.jobskillportalbackend.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class DialogflowService {

    private static final String PROJECT_ID = "jobbot-yj9t"; // Replace with your Google Cloud Project ID

    public String detectIntent(String sessionId, String message, Map<String, String> parameters) {
        try {
            ClassPathResource resource = new ClassPathResource("chatbot-key.json");
            InputStream inputStream = resource.getInputStream();

            GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);

            SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
            SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(() -> credentials).build();

            SessionsClient sessionsClient = SessionsClient.create(sessionsSettings);
            SessionName session = SessionName.of(PROJECT_ID, sessionId);

            TextInput.Builder textInput = TextInput.newBuilder().setText(message).setLanguageCode("en-US");
            QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

            QueryParameters.Builder queryParams = QueryParameters.newBuilder();
            Struct.Builder structBuilder = Struct.newBuilder();

            // Add job-related parameters to context
            parameters.forEach((key, value) -> {
                structBuilder.putFields(key, Value.newBuilder().setStringValue(value).build());
            });

            queryParams.setPayload(structBuilder.build());

            DetectIntentRequest request = DetectIntentRequest.newBuilder()
                    .setSession(session.toString())
                    .setQueryInput(queryInput)
                    .setQueryParams(queryParams.build())
                    .build();

            DetectIntentResponse response = sessionsClient.detectIntent(request);
            String fulfillmentText = response.getQueryResult().getFulfillmentText();

            // Replace placeholders with actual values
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                String placeholder = "{" + entry.getKey() + "}";
                fulfillmentText = fulfillmentText.replace(placeholder, entry.getValue());
            }

            return fulfillmentText;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error communicating with Dialogflow.";
        }
    }

}
