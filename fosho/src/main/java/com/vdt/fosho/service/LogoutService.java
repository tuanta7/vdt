package com.vdt.fosho.service;

import com.vdt.fosho.entity.Token;
import com.vdt.fosho.repository.TokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final Cookie[] cookies = request.getCookies();
        boolean hasRefreshToken = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh_token")) {
                    hasRefreshToken = true;
                    Token refreshToken = tokenRepository.findByToken(cookie.getValue()).orElse(null);
                    if (refreshToken != null) {
                        refreshToken.setRevoked(true);
                        refreshToken.setExpired(true);
                        tokenRepository.save(refreshToken);
                    }
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        if (!hasRefreshToken) {
            response.setStatus(401);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        final String jwt = authHeader.substring(7);
        Token token = tokenRepository.findByToken(jwt).orElse(null);
        if (token != null) {
            token.setRevoked(true);
            token.setExpired(true);
            tokenRepository.save(token);
        }
    }
}
