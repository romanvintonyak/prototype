package com.epam.helper;

import com.epam.service.RestAuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class RestHelper {

    @Autowired
    private RestAuthServiceImpl restAuthService;

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC_S = "Basic %s";

    public <T> T call(final String url, final Class<T> clazz) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, String.format(BASIC_S, restAuthService.getCredentials().getBase64Presentation()));
        HttpEntity<String> request = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, request, clazz).getBody();
    }
}
