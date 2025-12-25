package com.example.BackEnd.security.auth.dto;

import java.security.Principal;
import java.util.*;
import lombok.*;

@RequiredArgsConstructor
public class CustomMemberPrincipal implements Principal {

    private final String username;
    private final List<String> roles;

    @Override
    public String getName() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }

}
