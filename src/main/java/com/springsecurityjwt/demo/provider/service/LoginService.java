package com.springsecurityjwt.demo.provider.service;

import com.springsecurityjwt.demo.core.security.AuthToken;
import com.springsecurityjwt.demo.core.security.Role;
import com.springsecurityjwt.demo.core.service.LoginUseCase;
import com.springsecurityjwt.demo.core.service.dto.MemberDTO;
import com.springsecurityjwt.demo.provider.security.JwtAuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;
    private final static long LOGIN_RETENTION_MINUTES = 30;

    public Optional<MemberDTO> login(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Role role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .map(Role::of)
                .orElse(Role.UNKNOWN);

        MemberDTO memberDTO = MemberDTO.builder()
                .userName("bella")
                .email(email)
                .role(role)
                .build();
        return Optional.ofNullable(memberDTO);
    }

    @Override
    public AuthToken createAuthToken(MemberDTO memberDTO) {
        Date expiredDate = Date.from(LocalDateTime.now()
                .plusMinutes(LOGIN_RETENTION_MINUTES)
                .atZone(ZoneId.systemDefault()).toInstant());
        return jwtAuthTokenProvider.createAuthToken(memberDTO.getEmail(), memberDTO.getRole().getCode(), expiredDate);
    }
}
