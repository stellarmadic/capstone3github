package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    private final String baseUrl = "http://localhost:8080/account";
    private RestTemplate restTemplate = new RestTemplate();

    private final AuthenticatedUser currentUser;
    private String authToken = null;


    public AccountService(RestTemplate restTemplate, AuthenticatedUser currentUser) {
        this.restTemplate = restTemplate;
        this.currentUser = currentUser;
    }
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public BigDecimal getBalance() {
        BigDecimal balance = null;
        System.out.println("Account service started");
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(currentUser.getToken());
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<BigDecimal> response = restTemplate.exchange(baseUrl + "/balance/" + currentUser.getUser().getId(), HttpMethod.GET, entity, BigDecimal.class);
            balance = response.getBody();
            System.out.println("Account service concluded");
        } catch (Exception e) {
            BasicLogger.log(e.getMessage());
        }
        return balance;

    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
