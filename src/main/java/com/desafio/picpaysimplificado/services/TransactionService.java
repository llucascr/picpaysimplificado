package com.desafio.picpaysimplificado.services;

import com.desafio.picpaysimplificado.domain.transaction.Transaction;
import com.desafio.picpaysimplificado.domain.user.User;
import com.desafio.picpaysimplificado.dtos.transaction.TransactionRequestDTO;
import com.desafio.picpaysimplificado.dtos.transaction.TransactionResponseDTO;
import com.desafio.picpaysimplificado.exception.TransactionNotAuthorized;
import com.desafio.picpaysimplificado.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;

    private final UserService userService;
    private final NotificationService notificationService;

    private final RestTemplate restTemplate;

    public TransactionResponseDTO createTransaction(TransactionRequestDTO dto) {
        User sender = userService.findUserById(dto.senderId());
        User receiver = userService.findUserById(dto.receiverId());

        userService.validateTransaction(sender, dto.value());

        if (authorizeTransaction(sender, dto.value())) {
            throw new TransactionNotAuthorized("Transação não autorizada");
        }

        Transaction transaction = Transaction.builder()
                .amount(dto.value())
                .sender(sender)
                .receiver(receiver)
                .timestamp(LocalDateTime.now())
                .build();

        sender.setBalance(sender.getBalance().subtract(dto.value()));
        receiver.setBalance(receiver.getBalance().add(dto.value()));

        transactionRepository.save(transaction);
        userService.saveUser(sender);
        userService.saveUser(receiver);

        notificationService.sendNotification(sender, "Transação realizada com sucesso!");
        notificationService.sendNotification(receiver, "Transação recebida com sucesso!");

        return transaction.toResponse();

    }

    // funciona mas ele retorna uma mensagem de erro 
    private boolean authorizeTransaction(User sender, BigDecimal amount) {
        try {

            ResponseEntity<Map> response = restTemplate
                    .getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> body = response.getBody();

                Map<String, Object> data = (Map<String, Object>) body.get("data");

                if (data != null && data.get("authorization") != null) {
                    return (boolean) data.get("authorization");
                }
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

}
