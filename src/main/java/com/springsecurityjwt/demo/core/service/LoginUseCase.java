package com.springsecurityjwt.demo.core.service;

import com.springsecurityjwt.demo.core.security.AuthToken;
import com.springsecurityjwt.demo.core.service.dto.MemberDTO;

import java.util.Optional;

public interface LoginUseCase {
    Optional<MemberDTO> login(String id, String password);
    AuthToken createAuthToken(MemberDTO memberDTO);
}
