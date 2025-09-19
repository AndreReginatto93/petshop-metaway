package com.example.petshop.controllers;


import com.example.petshop.dtos.usuario.AuthenticationRecordDTO;
import com.example.petshop.dtos.usuario.LoginResponseRecordDTO;
import com.example.petshop.dtos.usuario.RegisterRecordDto;
import com.example.petshop.entities.usuario.UserEntity;
import com.example.petshop.repositories.UsuarioRepository;
import com.example.petshop.services.TokenService;
import com.example.petshop.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
public class AutenticacaoController {
    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager authenticationManager, UsuarioService usuarioService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.usuarioService = usuarioService;
        this.tokenService = tokenService;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseRecordDTO> login(@RequestBody @Valid AuthenticationRecordDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserEntity) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseRecordDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRecordDto registerRecordDto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.saveUser(registerRecordDto));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("Usuario já registrado")) {
                Map<String, String> errorBody = Map.of(
                        "error", "Unprocessable Entity",
                        "message", "Usuário já registrado"
                );
                return ResponseEntity.unprocessableEntity().body(errorBody);
            }

            // fallback para outros IllegalArgumentException
            Map<String, String> errorBody = Map.of(
                    "error", "Bad Request",
                    "message", e.getMessage()
            );
            return ResponseEntity.badRequest().body(errorBody);
        }
    }
}
