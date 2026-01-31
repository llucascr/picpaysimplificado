package com.desafio.picpaysimplificado.dtos.user;

import com.desafio.picpaysimplificado.domain.user.User;
import com.desafio.picpaysimplificado.domain.user.UserType;

import java.math.BigDecimal;

public record UserRequestDTO(
        String firstName,
        String lastName,
        String document,
        String email,
        String password,
        BigDecimal balance,
        UserType userType
) {
    public User toUser() {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .document(document)
                .email(email)
                .password(password)
                .balance(balance)
                .userType(userType)
                .build();
    }

    public UserResponseDTO toResponse() {
        return new UserResponseDTO(firstName, lastName, document, email, balance, userType);
    }
}
