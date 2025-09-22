package com.example.BackEnd.security.auth.dto;

import lombok.RequiredArgsConstructor;

import java.security.Principal;

@RequiredArgsConstructor
public class CustomMemberPrincipal implements Principal {

    private final String username;

    @Override
    public String getName() {
        return username;
    }
}
