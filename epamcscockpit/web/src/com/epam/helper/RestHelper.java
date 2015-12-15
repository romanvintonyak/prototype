package com.epam.helper;

import com.epam.service.RestAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

/**
 * Indented to call rest services
 */
public class RestHelper {

    @Autowired
    private RestAuthService restAuthService;

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC_PARAMS = "Basic %s";

    /**
     * Perform call to rest with credentials
     * @param url requested url
     * @param clazz mapped class
     * @return object from json
     */
    public <T> T call(final String url, final Class<T> clazz) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, String.format(BASIC_PARAMS, restAuthService.getCredentials().getBase64Presentation()));
        HttpEntity<String> request = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, request, clazz).getBody();
    }
}
