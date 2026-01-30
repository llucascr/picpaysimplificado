package com.desafio.picpaysimplificado.exception;

public class UserCannotCompleteTransaction extends RuntimeException {
    public UserCannotCompleteTransaction(String message) {
        super(message);
    }
}
