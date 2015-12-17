package com.epam.helper;

import com.epam.service.RestAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
     *
     * @param url   requested url
     * @param clazz mapped class
     * @return object from json
     */
    public <T> T call(final String url, final Class<T> clazz) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        return restTemplate.exchange(url, HttpMethod.GET, request, clazz).getBody();
    }

    /**
     * Perform call to rest with credentials and JSON body
     *
     * @param url        requested url
     * @param clazz      mapped class
     * @param jsonObject json object
     * @return object from json
     */
    public <T, R> T call(final String url, final Class<T> clazz, final R jsonObject) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<R> request = new HttpEntity<>(jsonObject, headers);
        return restTemplate.exchange(url, HttpMethod.POST, request, clazz).getBody();
    }

    /**
     * Set auth to header
     *
     * @return http header
     */
    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, String.format(BASIC_PARAMS, restAuthService.getCredentials().getBase64Presentation()));
        return headers;
    }
}
