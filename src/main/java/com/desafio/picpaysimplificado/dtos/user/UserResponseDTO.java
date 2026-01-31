package com.desafio.picpaysimplificado.dtos.user;

import com.desafio.picpaysimplificado.domain.user.UserType;

import java.math.BigDecimal;

public record UserResponseDTO(
        String firstName,
        String lastName,
        String document,
        String email,
        BigDecimal balance,
        UserType userType
) { }
