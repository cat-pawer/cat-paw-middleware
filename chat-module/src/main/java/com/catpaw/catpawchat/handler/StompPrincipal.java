package com.catpaw.catpawchat.handler;

import lombok.Setter;

import java.security.Principal;

@Setter
public class StompPrincipal implements Principal {

    private String authToken;

    public StompPrincipal(String token) {
        this.authToken = token;
    }

    @Override
    public String getName() {
        return this.authToken;
    }
}
