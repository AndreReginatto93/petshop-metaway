package com.example.petshop.entities.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;//CPF
    private String nome;
    private String password;
    private UserRole perfil;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.perfil == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ADMIN"));
        else return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getUsername() {
        return nome;
    }
}
