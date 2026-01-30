package com.desafio.picpaysimplificado.services;

import com.desafio.picpaysimplificado.domain.user.User;
import com.desafio.picpaysimplificado.domain.user.UserType;
import com.desafio.picpaysimplificado.exception.DataNotFound;
import com.desafio.picpaysimplificado.exception.UserCannotCompleteTransaction;
import com.desafio.picpaysimplificado.exception.UserWithInsuffiientBalance;
import com.desafio.picpaysimplificado.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {

        if (sender.getUserType() == UserType.MERCHANT) {
            throw new UserCannotCompleteTransaction("Usuário do tipo lojista não está autorizado a realizar transação");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new UserWithInsuffiientBalance("Saldo insuficiente");
        }

    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new DataNotFound("Usuário com id " + id + "não encontrado"));
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

}
