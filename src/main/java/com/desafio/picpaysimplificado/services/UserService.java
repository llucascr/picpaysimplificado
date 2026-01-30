package com.desafio.picpaysimplificado.services;

import com.desafio.picpaysimplificado.domain.user.User;
import com.desafio.picpaysimplificado.domain.user.UserType;
import com.desafio.picpaysimplificado.dtos.UserRequestDTO;
import com.desafio.picpaysimplificado.dtos.UserResponseDTO;
import com.desafio.picpaysimplificado.exception.DataNotFound;
import com.desafio.picpaysimplificado.exception.UserCannotCompleteTransaction;
import com.desafio.picpaysimplificado.exception.UserWithInsuffiientBalance;
import com.desafio.picpaysimplificado.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void validateTransaction(User sender, BigDecimal amount) {

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

    public UserResponseDTO createUser(UserRequestDTO dto) {
        saveUser(dto.toUser());
        return dto.toResponse();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Page<UserResponseDTO> findAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);

        return users.map(user -> {
            return new UserResponseDTO(
                    user.getFirstName(),
                    user.getLastName(),
                    user.getDocument(),
                    user.getEmail(),
                    user.getBalance(),
                    user.getUserType()
            );
        });

    }

}
