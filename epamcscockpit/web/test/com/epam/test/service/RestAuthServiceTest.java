package com.epam.test.service;

import com.epam.dto.Credentials;
import com.epam.service.RestAuthServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RestAuthServiceTest {

    @InjectMocks
    private RestAuthServiceImpl restAuthService;

    @Mock
    private UserDetails userDetails;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private static Authentication authentication;

    private static final String USER = "user";
    private static final char[] PASSWORD = "password".toCharArray();

    private static final Credentials CREDENTIALS = new Credentials(USER, PASSWORD);
    private static final String PASSWORD_STRING = String.valueOf(CREDENTIALS.getPassword());

    static {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(CREDENTIALS.getLogin(), CREDENTIALS.getPassword()));
    }

    @Test
    public void shouldReturnCredentials() throws Exception {
        when(userDetailsService.loadUserByUsername(USER)).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(CREDENTIALS.getLogin());
        when(userDetails.getPassword()).thenReturn(PASSWORD_STRING);

        Credentials credentials = restAuthService.getCredentials();

        assertThat(credentials.getLogin(), is(USER));
        assertThat(credentials.getPassword(), is(PASSWORD));
    }
}
