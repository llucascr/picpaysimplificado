package com.desafio.picpaysimplificado.exception;

public class UserWithInsuffiientBalance extends RuntimeException {
    public UserWithInsuffiientBalance(String message) {
        super(message);
    }
}
