package com.example.petshop.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.petshop.infra.security.SecurityConfigurations;
import com.example.petshop.repositories.UsuarioRepository;
import com.example.petshop.services.ClienteService;
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

@Import(SecurityConfigurations.class)
@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private ClienteService clienteService;

    @Autowired private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();
    }

    @Test
    void contextLoads() throws Exception {
        assertThat(clienteService).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @Nested
    class UnauthenticatedTests {
        @Test
        void getOwnClienteUnauthorized() throws Exception {
            mockMvc.perform(get("/api/v1/clientes/me")).andDo(print()).andExpect(status().isUnauthorized());
        }

        @Test
        void getAllClientesUnauthorized() throws Exception {
            mockMvc.perform(get("/api/v1/clientes")).andDo(print()).andExpect(status().isUnauthorized());
        }

        @Test
        void getAllClienteById() throws Exception {
            mockMvc.perform(get("/api/v1/clientes")).andDo(print()).andExpect(status().isUnauthorized());
        }

        @Test
        void saveCliente() throws Exception {
            mockMvc.perform(post("/api/v1/clientes")).andDo(print()).andExpect(status().isUnauthorized());
        }

        @Test
        void updateCliente() throws Exception {
            mockMvc.perform(put("/api/v1/clientes/1")).andDo(print()).andExpect(status().isUnauthorized());
        }

        @Test
        void updateOwnCliente() throws Exception {
            mockMvc.perform(put("/api/v1/clientes/me")).andDo(print()).andExpect(status().isUnauthorized());
        }

        @Test
        void deleteCliente() throws Exception {
            mockMvc.perform(delete("/api/v1/clientes/1")).andDo(print()).andExpect(status().isUnauthorized());
        }
    }


    @Nested
    @WithMockUser(roles = {"USER"})
    class UserTests {
        @Test
        void getOwnClienteAuthorized() throws Exception {
            mockMvc.perform(get("/api/v1/clientes/me")).andDo(print()).andExpect(status().isOk());
        }

        @Test
        void getAllClientesUserForbidden() throws Exception {
            mockMvc.perform(get("/api/v1/clientes")).andDo(print()).andExpect(status().isForbidden());
        }

        @Test
        void getAllClienteById() throws Exception {
            mockMvc.perform(get("/api/v1/clientes")).andDo(print()).andExpect(status().isForbidden());
        }

        @Test
        void saveCliente() throws Exception {
            mockMvc.perform(post("/api/v1/clientes")).andDo(print()).andExpect(status().isForbidden());
        }

        @Test
        void updateCliente() throws Exception {
            mockMvc.perform(put("/api/v1/clientes/1")).andDo(print()).andExpect(status().isForbidden());
        }

        @Test
        void updateOwnCliente() throws Exception {
            mockMvc.perform(put("/api/v1/clientes/me")
                    .content("{}")
                    .contentType("application/json")
            ).andDo(print()).andExpect(status().isOk());
        }

        @Test
        void deleteCliente() throws Exception {
            mockMvc.perform(delete("/api/v1/clientes/1")).andDo(print()).andExpect(status().isForbidden());
        }
    }

    @Nested
    @WithMockUser(roles = {"ADMIN"})
    class AdminTests {
        @Test
        void getOwnClienteAdminAuthorized() throws Exception {
            mockMvc.perform(get("/api/v1/clientes/me")).andDo(print()).andExpect(status().isOk());
        }

        @Test
        void getAllClientesAuthorized() throws Exception {
            mockMvc.perform(get("/api/v1/clientes")).andDo(print()).andExpect(status().isOk())
                    .andExpect(content().string(containsString("[]")));
        }

        @Test
        void getAllClienteById() throws Exception {
            mockMvc.perform(get("/api/v1/clientes")).andDo(print()).andExpect(status().isOk());
        }

        @Test
        void saveClienteWhitoutJson() throws Exception {
            mockMvc.perform(post("/api/v1/clientes")).andDo(print()).andExpect(status().isUnprocessableEntity());
        }

        @Test
        void saveCliente() throws Exception {
            mockMvc.perform(post("/api/v1/clientes")
                    .content("{}")
                    .contentType("application/json")
            ).andDo(print()).andExpect(status().isCreated());
        }

        @Test
        void updateClienteWhitoutJson() throws Exception {
            mockMvc.perform(put("/api/v1/clientes/1")).andDo(print()).andExpect(status().isUnprocessableEntity());
        }

        @Test
        void updateCliente() throws Exception {
            mockMvc.perform(put("/api/v1/clientes/1")
                    .content("{}")
                    .contentType("application/json")
            ).andDo(print()).andExpect(status().isNotFound());
        }

        @Test
        void updateOwnCliente() throws Exception {
            mockMvc.perform(put("/api/v1/clientes/me")
                    .content("{}")
                    .contentType("application/json")
            ).andDo(print()).andExpect(status().isOk());
        }

        @Test
        void deleteCliente() throws Exception {
            mockMvc.perform(delete("/api/v1/clientes/1")).andDo(print()).andExpect(status().isOk());
        }
    }
}