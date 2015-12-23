package com.epam.test.helper;

import com.epam.helper.RestHelper;
import org.springframework.http.HttpMethod;

/**
 * Mock for rest helper. Need to mock all rest calls in unit tests.
 */
public class RestHelperMock extends RestHelper {

    private Object mockResponseDto;

    @SuppressWarnings("unchecked")
    @Override
    public <T> T call(String url, Class<T> clazz) {
        return (T) mockResponseDto;
    }

    @Override
    public <T, R> T call(String url, Class<T> clazz, R jsonObject, HttpMethod httpMethod) {
        return call(url, clazz);
    }

    public Object getMockResponseDto() {
        return mockResponseDto;
    }

    public void setMockResponseDto(Object mockResponseDto) {
        this.mockResponseDto = mockResponseDto;
    }
}
