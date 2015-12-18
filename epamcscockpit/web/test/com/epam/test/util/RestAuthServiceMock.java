package com.epam.test.util;

import com.epam.dto.Credentials;
import com.epam.service.RestAuthService;

public class RestAuthServiceMock implements RestAuthService {

    private static final String USER = "admin";
    private static final char[] PASSWORD = "nimda".toCharArray();

    private static final Credentials CREDENTIALS = new Credentials(USER, PASSWORD);

    @Override
    public Credentials getCredentials() {
        return RestAuthServiceMock.CREDENTIALS;
    }
}
