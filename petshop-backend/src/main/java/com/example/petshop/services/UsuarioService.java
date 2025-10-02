package com.example.petshop.services;


import com.example.petshop.dtos.usuario.RegisterRecordDto;
import com.example.petshop.dtos.usuario.UsuarioReadDTO;
import com.example.petshop.entities.RacaEntity;
import com.example.petshop.entities.usuario.UserEntity;
import com.example.petshop.handler.UserAlreadyExistsException;
import com.example.petshop.repositories.UsuarioRepository;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioReadDTO> getAllUsuarios() {
        List<UserEntity> users = usuarioRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<UsuarioReadDTO> usuarioReadDTOS = users.stream()
                .map(user -> new UsuarioReadDTO(
                        user.getId(),
                        user.getLogin(),
                        user.getNome(),
                        user.getRole().name()
                )).toList();
        return usuarioReadDTOS;
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

    @Transactional
    public void deleteUsuario(Long id){
        usuarioRepository.deleteById(id);
    }
}
