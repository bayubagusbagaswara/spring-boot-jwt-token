package com.bayu.springboot3.jwt.service.impl;

import com.bayu.springboot3.jwt.config.JwtService;
import com.bayu.springboot3.jwt.dto.AuthenticationRequest;
import com.bayu.springboot3.jwt.dto.AuthenticationResponse;
import com.bayu.springboot3.jwt.dto.RegisterRequest;
import com.bayu.springboot3.jwt.model.Role;
import com.bayu.springboot3.jwt.model.Token;
import com.bayu.springboot3.jwt.model.TokenType;
import com.bayu.springboot3.jwt.model.User;
import com.bayu.springboot3.jwt.repository.TokenRepository;
import com.bayu.springboot3.jwt.repository.UserRepository;
import com.bayu.springboot3.jwt.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        saveUserToken(savedUser, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        String jwtToken = jwtService.generateToken(user);

        // the token will always update when the authentication is successful
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User savedUser, String jwtToken) {
        Token token = Token.builder()
                .user(savedUser)
                .tokenValue(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(Boolean.FALSE)
                .revoked(Boolean.FALSE)
                .build();

        tokenRepository.save(token);
    }

}
