package com.jwtbearer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwtbearer.model.RequestCredentials;
import com.jwtbearer.model.RequestMessage;
import com.jwtbearer.model.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class FirstTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    @ParameterizedTest
    @ArgumentsSource(RequestCredentialsPostProvider.class)
    public void postLoginTest(Map<String, Object> a) throws JsonProcessingException {
        User user = (User) a.get("user");
        String name = user.getName();
        String pass = user.getPassword();

        HttpHeaders headersLogin = new HttpHeaders();
        headersLogin.setContentType(MediaType.APPLICATION_JSON);

        RequestCredentials requestCredentials = new RequestCredentials(name, pass);
        String jsonLogin = new ObjectMapper().writeValueAsString(requestCredentials);

        HttpEntity<String> entityLogin = new HttpEntity<>(jsonLogin, headersLogin);
        ResponseEntity<Map> responseLogin = restTemplate.postForEntity("http://localhost:" + port + "/auth/login", entityLogin, Map.class);
        String token = responseLogin.getBody().get("token").toString();


        HttpHeaders headersMessage = new HttpHeaders();
        headersMessage.setContentType(MediaType.APPLICATION_JSON);
        headersMessage.set("Authorization", "Bearer " + token);


        List<String> messageList = (List<String>) a.get("messageList");

        for (String s : messageList) {

            RequestMessage requestMessage = new RequestMessage(name, s);
            String json = new ObjectMapper().writeValueAsString(requestMessage);

            HttpEntity<String> entity = new HttpEntity<>(json, headersMessage);
            ResponseEntity<String> responseSaveMessage = restTemplate.postForEntity("http://localhost:" + port + "/message", entity, String.class);
            assertEquals(HttpStatus.OK, responseSaveMessage.getStatusCode());
        }

        List<String> historyList = (List<String>) a.get("history");
        List<Integer> expectedResponse = (List<Integer>) a.get("expectedResp");

        for (int i = 0; i < historyList.size(); i++) {
            RequestMessage requestMessage = new RequestMessage(name, historyList.get(i));
            String json = new ObjectMapper().writeValueAsString(requestMessage);

            HttpEntity<String> entity = new HttpEntity<>(json, headersMessage);
            ResponseEntity<List> responseGetMessages = restTemplate.postForEntity("http://localhost:" + port + "/message", entity, List.class);

            List history = responseGetMessages.getBody();

            assertEquals(expectedResponse.get(i + 1), history.size());
            assertEquals(HttpStatus.OK, responseGetMessages.getStatusCode());
        }
    }
}