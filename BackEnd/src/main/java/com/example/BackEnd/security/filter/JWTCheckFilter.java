package com.example.BackEnd.security.filter;

import com.example.BackEnd.common.enums.error_codes.AuthError;
import com.example.BackEnd.common.exception.BusinessException;
import com.example.BackEnd.common.utils.JWTUtil;
import com.example.BackEnd.security.auth.dto.CustomMemberPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTCheckFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();

        return pathMatcher.match("/sampleTest", path)
                || pathMatcher.match("/auth/send-otp", path)
                || pathMatcher.match("/auth/verify-otp", path)
                || pathMatcher.match("/auth/login", path)
                || pathMatcher.match("/auth/refresh", path)
                || pathMatcher.match("/members/**", path);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        String token = resolveToken(request);

        // 토큰 자체를 검증
        if (token == null || !jwtUtil.isValidated(token)) {
            handleException(response, new BusinessException(AuthError.INVALID_ACCESS_TOKEN));
            return;
        }

        // 해당 token이 access token인지 확인
        String tokenType = jwtUtil.parseClaims(token).get("type", String.class);
        if (!"access".equals(tokenType)) {
            handleException(response, new BusinessException(AuthError.INVALID_ACCESS_TOKEN));
            return;
        }

        // 블랙리스트 검증
        if (jwtUtil.isBlackListed(token)) {
            handleException(response, new BusinessException(AuthError.INVALID_ACCESS_TOKEN));
            return;
        }

        String username = jwtUtil.parseClaims(token).getSubject();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                new CustomMemberPrincipal(username),
                null
            );

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        return header.substring(7);
    }

    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\" : \"" + e.getMessage() + "\"}");
    }

}
