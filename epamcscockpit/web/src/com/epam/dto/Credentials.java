package com.epam.dto;

import org.springframework.security.crypto.codec.Base64;

import java.util.Arrays;

public final class Credentials {

    private final String login;
    private final char[] password;

    public Credentials(String login, char[] password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public char[] getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Credentials that = (Credentials) o;

        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        return Arrays.equals(password, that.password);

    }

    public final String getBase64Presentation() {
        String credentials = String.format("%s:%s", login, String.valueOf(password));
        byte[] encodedCredentials = Base64.encode(credentials.getBytes());
        return new String(encodedCredentials);
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(password);
        return result;
    }

    @Override
    public String toString() {
        return "Credentials{" +
                "login='" + login + '\'' +
                ", password=" + Arrays.toString(password) +
                '}';
    }
}
