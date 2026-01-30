package com.desafio.picpaysimplificado.exception;

public class TransactionNotAuthorized extends RuntimeException {
    public TransactionNotAuthorized(String message) {
        super(message);
    }
}
