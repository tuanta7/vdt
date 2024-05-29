package com.vdt.fosho.service;

import com.vdt.fosho.entity.Token;
import com.vdt.fosho.entity.TokenType;
import com.vdt.fosho.repository.TokenRepository;
import com.vdt.fosho.dto.LoginDTO;
import com.vdt.fosho.dto.RegisterDTO;
import com.vdt.fosho.entity.Role;
import com.vdt.fosho.entity.User;
import com.vdt.fosho.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public void saveToken(User user, String jwt) {
        revokeAllTokens(user);
        Token token = Token.builder()
                .token(jwt)
                .type(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .user(user)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllTokens(User user) {
        List<Token> validTokens = tokenRepository.findAllValidTokensByUserId(user.getId());
        if (validTokens.isEmpty()) {
            return;
        }
        validTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validTokens);
    }

    public User register(RegisterDTO registerDTO) {
        if (!registerDTO.getConfirmPassword().equals(registerDTO.getPassword())) {
            throw new IllegalArgumentException("Password and confirm password do not match");
        }

        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = User.builder()
                .fullName(registerDTO.getFullName())
                .email(registerDTO.getEmail())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .role(Role.USER)
                .build();

        return userRepository.save(user);
    }

    public User login(LoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );
        return userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found")
                );
    }

    public void logout(String token) {
    }
}
