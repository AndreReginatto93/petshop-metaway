package com.example.petshop.config;

import com.example.petshop.dtos.usuario.RegisterRecordDto;
import com.example.petshop.entities.usuario.UserRole;
import com.example.petshop.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class AdminUserInitializer {

    private final UsuarioService usuarioService;

    public AdminUserInitializer(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Bean
    CommandLineRunner initAdminUser() {
        return args -> {
            try{
                UserDetails userDetails = usuarioService.loadUserByUsername("admin");
                if (userDetails != null){
                    System.out.println("Usuário admin/admin já existe!"+userDetails);
                    return;
                }else{
                    createUserAdmin();
                }
            } catch (UsernameNotFoundException ex) {
                createUserAdmin();
            }
        };
    }

    void createUserAdmin(){
        RegisterRecordDto admin = new RegisterRecordDto(
                "admin",
                "admin",
                "admin",
                UserRole.ADMIN
        );

        usuarioService.saveUser(admin);

        System.out.println("Usuário admin/admin criado com sucesso!");
    }
}
