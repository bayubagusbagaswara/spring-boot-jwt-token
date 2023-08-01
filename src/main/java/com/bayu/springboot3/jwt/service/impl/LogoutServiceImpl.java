package com.bayu.springboot3.jwt.service.impl;

import com.bayu.springboot3.jwt.model.Token;
import com.bayu.springboot3.jwt.repository.TokenRepository;
import com.bayu.springboot3.jwt.service.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        jwt = authHeader.substring(7);

        Token storedToken = tokenRepository.findByTokenValue(jwt)
                .orElse(null);

        if (storedToken != null) {
            storedToken.setExpired(Boolean.TRUE);
            storedToken.setRevoked(Boolean.TRUE);
            tokenRepository.save(storedToken);
        }
    }

}
