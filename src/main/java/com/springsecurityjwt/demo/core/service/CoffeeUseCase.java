package com.springsecurityjwt.demo.core.service;

import com.springsecurityjwt.demo.core.service.dto.CoffeeDTO;

import java.util.List;
import java.util.Optional;

public interface CoffeeUseCase {
    Optional<List<CoffeeDTO>> findAll();
}
