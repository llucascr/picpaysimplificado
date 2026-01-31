package com.desafio.picpaysimplificado.dtos.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(
        BigDecimal amount,
        Long senderId,
        Long receiverId,
        LocalDateTime timestamp
) {
}
