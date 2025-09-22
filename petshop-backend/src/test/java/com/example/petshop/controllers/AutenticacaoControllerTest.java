package com.example.petshop.controllers;

import com.example.petshop.dtos.usuario.AuthenticationRecordDTO;
import com.example.petshop.infra.security.SecurityConfigurations;
import com.example.petshop.repositories.UsuarioRepository;
import com.example.petshop.services.AtendimentoService;
import com.example.petshop.services.TokenService;
import com.example.petshop.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfigurations.class)
@WebMvcTest(AutenticacaoController.class)
class AutenticacaoControllerTest {

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();
    }

    @Test
    void contextLoads() throws Exception {
        assertThat(usuarioService).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @Nested
    class UnauthenticatedTests {
        @Test
        void loginWithoutJson() throws Exception {
            mockMvc.perform(post("/api/v1/auth/login")).andDo(print()).andExpect(status().isUnprocessableEntity());
        }

        @Test
        void registerWithoutJson() throws Exception {
            mockMvc.perform(post("/api/v1/auth/register")).andDo(print()).andExpect(status().isUnauthorized());
        }

        @Test
        void registerWithJson() throws Exception {
            mockMvc.perform(post("/api/v1/auth/register")
                    .content("{}")
                    .contentType("application/json")
            ).andDo(print()).andExpect(status().isUnauthorized());
        }
    }

    @Nested
    @WithMockUser(username = "testuser", roles = {"USER"})
    class UserTests {
        @Test
        void loginWithoutJson() throws Exception {
            mockMvc.perform(post("/api/v1/auth/login")).andDo(print()).andExpect(status().isUnprocessableEntity());
        }

        @Test
        void registerWithoutJson() throws Exception {
            mockMvc.perform(post("/api/v1/auth/register")).andDo(print()).andExpect(status().isForbidden());
        }

        @Test
        void registerWithJson() throws Exception {
            mockMvc.perform(post("/api/v1/auth/register")
                    .content("{}")
                    .contentType("application/json")
            ).andDo(print()).andExpect(status().isForbidden());
        }
    }

    @Nested
    @WithMockUser(username = "teste", password = "asd", roles = {"ADMIN"})
    class AdminTests {
        @Test
        void loginWithoutJson() throws Exception {
            mockMvc.perform(post("/api/v1/auth/login")).andDo(print()).andExpect(status().isUnprocessableEntity());
        }

        @Test
        void registerWithoutJson() throws Exception {
            mockMvc.perform(post("/api/v1/auth/register")).andDo(print()).andExpect(status().isUnprocessableEntity());
        }

        @Test
        void registerWithJson() throws Exception {
            mockMvc.perform(post("/api/v1/auth/register")
                    .content("{}")
                    .contentType("application/json")
            ).andDo(print()).andExpect(status().isCreated());
        }
    }



}