package org.team8.authentication;

public class LoginResponse {
    public String token;
    public String role;

    public LoginResponse(String token, String role) {
        this.token = token;
    }
}
