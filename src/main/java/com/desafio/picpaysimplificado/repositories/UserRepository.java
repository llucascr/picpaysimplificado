package com.desafio.picpaysimplificado.repositories;

import com.desafio.picpaysimplificado.domain.user.User;
import com.desafio.picpaysimplificado.dtos.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByDocument(String document);

}
