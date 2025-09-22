package com.example.petshop.services;


import com.example.petshop.dtos.usuario.RegisterRecordDto;
import com.example.petshop.entities.usuario.UserEntity;
import com.example.petshop.handler.UserAlreadyExistsException;
import com.example.petshop.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public UserEntity saveUser(RegisterRecordDto registerRecordDto){
        if(usuarioRepository.findByLogin(registerRecordDto.login()) != null){
            throw new UserAlreadyExistsException("Usuario já registrado com login: " + registerRecordDto.login());
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRecordDto.password());
        UserEntity newUser = UserEntity.builder()
                .login(registerRecordDto.login())
                .nome(registerRecordDto.nome())
                .password(encryptedPassword)
                .role(registerRecordDto.role())
                .build();
        return usuarioRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(username);
    }
}
