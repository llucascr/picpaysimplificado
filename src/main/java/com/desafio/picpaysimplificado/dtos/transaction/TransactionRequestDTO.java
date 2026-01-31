package com.desafio.picpaysimplificado.dtos.transaction;

import java.math.BigDecimal;

public record TransactionRequestDTO(
        BigDecimal value,
        Long senderId,
        Long receiverId
) {
}
