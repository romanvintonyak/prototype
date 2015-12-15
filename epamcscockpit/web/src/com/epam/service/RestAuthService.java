package com.epam.service;

import com.epam.dto.Credentials;

public interface RestAuthService {
    /**
     * Return current credentials from the security context
     * @return login and password
     */
    Credentials getCredentials();
}
