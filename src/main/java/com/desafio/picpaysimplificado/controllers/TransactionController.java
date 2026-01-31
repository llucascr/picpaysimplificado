package com.desafio.picpaysimplificado.controllers;

import com.desafio.picpaysimplificado.dtos.transaction.TransactionRequestDTO;
import com.desafio.picpaysimplificado.dtos.transaction.TransactionResponseDTO;
import com.desafio.picpaysimplificado.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(
            @RequestBody TransactionRequestDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.createTransaction(dto));
    }

}
