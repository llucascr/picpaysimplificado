package com.desafio.picpaysimplificado.services;

import com.desafio.picpaysimplificado.domain.transaction.Transaction;
import com.desafio.picpaysimplificado.domain.user.User;
import com.desafio.picpaysimplificado.dtos.TransactionDTO;
import com.desafio.picpaysimplificado.exception.TransactionNotAuthorized;
import com.desafio.picpaysimplificado.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final UserService userService;

    private RestTemplate restTemplate;

    public  void createTransaction(TransactionDTO dto) {
        User sender = userService.findUserById(dto.senderId());
        User receiver = userService.findUserById(dto.receiverId());

        userService.validateTransaction(sender, dto.value());

        if (!authorizeTransaction(sender, dto.value())) {
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



    }

    private boolean authorizeTransaction(User sender, BigDecimal amount) {
        ResponseEntity<Map> authorizationResponse = restTemplate
                .getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

        if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
            String messageValue = authorizationResponse.getBody().get("message").toString();
            return  messageValue.equals("true");
        } else return false;
    }

}
