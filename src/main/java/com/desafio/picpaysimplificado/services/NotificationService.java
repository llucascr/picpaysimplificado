package com.desafio.picpaysimplificado.services;

import com.desafio.picpaysimplificado.domain.user.User;
import com.desafio.picpaysimplificado.dtos.NotificationDTO;
import com.desafio.picpaysimplificado.exception.NotificationMicroserviceDown;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) {

        NotificationDTO notificationRequest = new  NotificationDTO(user.getEmail(), message);

        ResponseEntity<String> notificationResponse = restTemplate
                .postForEntity("https://util.devi.tools/api/v1/notify", notificationRequest, String.class);

        if (notificationResponse.getStatusCode() !=  HttpStatus.OK) {
            log.info("Serviço de notificação está fora do ar");
            throw new NotificationMicroserviceDown("Erro ao enviar notificação");
        }

    }

}
