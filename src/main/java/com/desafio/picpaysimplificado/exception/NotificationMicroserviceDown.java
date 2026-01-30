package com.desafio.picpaysimplificado.exception;

public class NotificationMicroserviceDown extends RuntimeException {
    public NotificationMicroserviceDown(String message) {
        super(message);
    }
}
