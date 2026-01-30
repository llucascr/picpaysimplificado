package com.desafio.picpaysimplificado.controllers;

import com.desafio.picpaysimplificado.domain.user.User;
import com.desafio.picpaysimplificado.dtos.UserRequestDTO;
import com.desafio.picpaysimplificado.dtos.UserResponseDTO;
import com.desafio.picpaysimplificado.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

    @GetMapping
    public PagedModel<UserResponseDTO> findAllUsers(@PageableDefault Pageable pageable) {
        return new PagedModel<>(userService.findAllUsers(pageable));
    }

}
