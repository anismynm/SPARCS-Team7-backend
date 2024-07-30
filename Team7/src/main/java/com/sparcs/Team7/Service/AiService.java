package com.sparcs.Team7.Service;

import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Slf4j
public class AiService {

    @Autowired
    private OpenAiService openAiService;

    @Value("${openai.key}")
    private String openaiApiKey;

    public String generatePictureV2(String prompt) throws IOException, InterruptedException {
        String url = "https://api.openai.com/v1/images/generations";

        // JSON 문자열 생성
        String requestBody = String.format(
                "{\"model\":\"dall-e-3\",\"prompt\":\"%s\",\"n\":1,\"size\":\"1024x1024\"}",
                prompt);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + openaiApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 응답 본문에서 URL 추출
        String responseBody = response.body();
        int startIndex = responseBody.indexOf("https://"); // URL이 "https://"로 시작함
        int endIndex = responseBody.indexOf("\"", startIndex); // URL이 큰 따옴표로 끝남
        String imageUrl = responseBody.substring(startIndex, endIndex);

        log.info("===============imageUrl===============");
        log.info(imageUrl);

        return imageUrl;
    }
}
