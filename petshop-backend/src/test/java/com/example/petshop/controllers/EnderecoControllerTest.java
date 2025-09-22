package com.example.petshop.controllers;

import com.example.petshop.infra.security.SecurityConfigurations;
import com.example.petshop.repositories.UsuarioRepository;
import com.example.petshop.services.ContatoService;
import com.example.petshop.services.EnderecoService;
import com.example.petshop.services.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfigurations.class)
@WebMvcTest(EnderecoController.class)
class EnderecoControllerTest {

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private EnderecoService enderecoService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();
    }

    @Test
    void contextLoads() throws Exception {
        assertThat(enderecoService).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @Nested
    class UnauthenticatedTests {
        @Test
        void getOwnEnderecoUnauthorized() throws Exception {
            mockMvc.perform(get("/api/v1/enderecos/me")).andDo(print()).andExpect(status().isUnauthorized());
        }

        @Test
        void getAllEnderecosUnauthorized() throws Exception {
            mockMvc.perform(get("/api/v1/enderecos")).andDo(print()).andExpect(status().isUnauthorized());
        }

        @Test
        void getEnderecoById() throws Exception {
            mockMvc.perform(get("/api/v1/enderecos/1")).andDo(print()).andExpect(status().isUnauthorized());
        }

        @Test
        void saveEndereco() throws Exception {
            mockMvc.perform(post("/api/v1/enderecos")).andDo(print()).andExpect(status().isUnauthorized());
        }

        @Test
        void updateEndereco() throws Exception {
            mockMvc.perform(put("/api/v1/enderecos/1")).andDo(print()).andExpect(status().isUnauthorized());
        }

        @Test
        void updateOwnEndereco() throws Exception {
            mockMvc.perform(put("/api/v1/enderecos/me")).andDo(print()).andExpect(status().isUnauthorized());
        }

        @Test
        void deleteEndereco() throws Exception {
            mockMvc.perform(delete("/api/v1/enderecos/1")).andDo(print()).andExpect(status().isUnauthorized());
        }
    }




    @Nested
    @WithMockUser(roles = {"USER"})
    class UserTests {
        @Test
        void getOwnEndereco() throws Exception {
            mockMvc.perform(get("/api/v1/enderecos/me")).andDo(print()).andExpect(status().isOk());
        }

        @Test
        void getAllEnderecos() throws Exception {
            mockMvc.perform(get("/api/v1/enderecos")).andDo(print()).andExpect(status().isForbidden());
        }

        @Test
        void getEnderecoById() throws Exception {
            mockMvc.perform(get("/api/v1/enderecos/1")).andDo(print()).andExpect(status().isForbidden());
        }

        @Test
        void saveEndereco() throws Exception {
            mockMvc.perform(post("/api/v1/enderecos")).andDo(print()).andExpect(status().isForbidden());
        }

        @Test
        void updateEndereco() throws Exception {
            mockMvc.perform(put("/api/v1/enderecos/1")).andDo(print()).andExpect(status().isForbidden());
        }

        @Test
        void updateOwnEndereco() throws Exception {
            mockMvc.perform(put("/api/v1/enderecos/me/1")
                    .content("{}")
                    .contentType("application/json")
            ).andDo(print()).andExpect(status().isNotFound());
        }

        @Test
        void deleteEndereco() throws Exception {
            mockMvc.perform(delete("/api/v1/enderecos/1")).andDo(print()).andExpect(status().isForbidden());
        }
    }

    @Nested
    @WithMockUser(roles = {"ADMIN"})
    class AdminTests {
        @Test
        void getOwnEndereco() throws Exception {
            mockMvc.perform(get("/api/v1/enderecos/me")).andDo(print()).andExpect(status().isOk());
        }

        @Test
        void getAllEnderecos() throws Exception {
            mockMvc.perform(get("/api/v1/enderecos")).andDo(print()).andExpect(status().isOk())
                    .andExpect(content().string(containsString("[]")));
        }

        @Test
        void getEnderecoById() throws Exception {
            mockMvc.perform(get("/api/v1/enderecos/1")).andDo(print()).andExpect(status().isOk());
        }

        @Test
        void saveEnderecoWhitoutJson() throws Exception {
            mockMvc.perform(post("/api/v1/enderecos")).andDo(print()).andExpect(status().isUnprocessableEntity());
        }

        @Test
        void saveEndereco() throws Exception {
            mockMvc.perform(post("/api/v1/enderecos")
                    .content("{}")
                    .contentType("application/json")
            ).andDo(print()).andExpect(status().isCreated());
        }

        @Test
        void updateEnderecoWhitoutJson() throws Exception {
            mockMvc.perform(put("/api/v1/enderecos/1")).andDo(print()).andExpect(status().isUnprocessableEntity());
        }

        @Test
        void updateEndereco() throws Exception {
            mockMvc.perform(put("/api/v1/enderecos/1")
                    .content("{}")
                    .contentType("application/json")
            ).andDo(print()).andExpect(status().isNotFound());
        }

        @Test
        void updateOwnEndereco() throws Exception {
            mockMvc.perform(put("/api/v1/enderecos/me/1")
                    .content("{}")
                    .contentType("application/json")
            ).andDo(print()).andExpect(status().isNotFound());
        }


        @Test
        void deleteEndereco() throws Exception {
            mockMvc.perform(delete("/api/v1/enderecos/1")).andDo(print()).andExpect(status().isOk());
        }
    }
}