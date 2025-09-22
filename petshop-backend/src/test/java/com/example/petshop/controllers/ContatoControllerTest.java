package com.example.petshop.controllers;

import com.example.petshop.infra.security.SecurityConfigurations;
import com.example.petshop.repositories.UsuarioRepository;
import com.example.petshop.services.ContatoService;
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
@WebMvcTest(ContatoController.class)
class ContatoControllerTest {

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private ContatoService contatoService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();
    }

    @Test
    void contextLoads() throws Exception {
        assertThat(contatoService).isNotNull();
        assertThat(mockMvc).isNotNull();
    }


    @Nested
    class UnauthenticatedTests {
        @Test
        void getOwnContatoUnauthorized() throws Exception {
            mockMvc.perform(get("/api/v1/contatos/me")).andDo(print()).andExpect(status().isUnauthorized());
        }

        @Test
        void getAllContatosUnauthorized() throws Exception {
            mockMvc.perform(get("/api/v1/contatos")).andDo(print()).andExpect(status().isUnauthorized());
        }

        @Test
        void getContatoById() throws Exception {
            mockMvc.perform(get("/api/v1/contatos/1")).andDo(print()).andExpect(status().isUnauthorized());
        }

        @Test
        void saveContato() throws Exception {
            mockMvc.perform(post("/api/v1/contatos")).andDo(print()).andExpect(status().isUnauthorized());
        }

        @Test
        void updateContato() throws Exception {
            mockMvc.perform(put("/api/v1/contatos/1")).andDo(print()).andExpect(status().isUnauthorized());
        }

        @Test
        void updateOwnContato() throws Exception {
            mockMvc.perform(put("/api/v1/contatos/me")).andDo(print()).andExpect(status().isUnauthorized());
        }

        @Test
        void deleteContato() throws Exception {
            mockMvc.perform(delete("/api/v1/contatos/1")).andDo(print()).andExpect(status().isUnauthorized());
        }
    }




    @Nested
    @WithMockUser(roles = {"USER"})
    class UserTests {
        @Test
        void getOwnContato() throws Exception {
            mockMvc.perform(get("/api/v1/contatos/me")).andDo(print()).andExpect(status().isOk());
        }

        @Test
        void getAllContatos() throws Exception {
            mockMvc.perform(get("/api/v1/contatos")).andDo(print()).andExpect(status().isForbidden());
        }

        @Test
        void getContatoById() throws Exception {
            mockMvc.perform(get("/api/v1/contatos/1")).andDo(print()).andExpect(status().isForbidden());
        }

        @Test
        void saveContato() throws Exception {
            mockMvc.perform(post("/api/v1/contatos")).andDo(print()).andExpect(status().isForbidden());
        }

        @Test
        void updateContato() throws Exception {
            mockMvc.perform(put("/api/v1/contatos/1")).andDo(print()).andExpect(status().isForbidden());
        }

        @Test
        void updateOwnContato() throws Exception {
            mockMvc.perform(put("/api/v1/contatos/me/1")
                    .content("{}")
                    .contentType("application/json")
            ).andDo(print()).andExpect(status().isNotFound());
        }

        @Test
        void deleteContato() throws Exception {
            mockMvc.perform(delete("/api/v1/contatos/1")).andDo(print()).andExpect(status().isForbidden());
        }
    }

    @Nested
    @WithMockUser(roles = {"ADMIN"})
    class AdminTests {
        @Test
        void getOwnContato() throws Exception {
            mockMvc.perform(get("/api/v1/contatos/me")).andDo(print()).andExpect(status().isOk());
        }

        @Test
        void getAllContatos() throws Exception {
            mockMvc.perform(get("/api/v1/contatos")).andDo(print()).andExpect(status().isOk())
                    .andExpect(content().string(containsString("[]")));
        }

        @Test
        void getContatoById() throws Exception {
            mockMvc.perform(get("/api/v1/contatos/1")).andDo(print()).andExpect(status().isOk());
        }

        @Test
        void saveContatoWhitoutJson() throws Exception {
            mockMvc.perform(post("/api/v1/contatos")).andDo(print()).andExpect(status().isUnprocessableEntity());
        }

        @Test
        void saveContato() throws Exception {
            mockMvc.perform(post("/api/v1/contatos")
                    .content("{}")
                    .contentType("application/json")
            ).andDo(print()).andExpect(status().isCreated());
        }

        @Test
        void updateContatoWhitoutJson() throws Exception {
            mockMvc.perform(put("/api/v1/contatos/1")).andDo(print()).andExpect(status().isUnprocessableEntity());
        }

        @Test
        void updateContato() throws Exception {
            mockMvc.perform(put("/api/v1/contatos/1")
                    .content("{}")
                    .contentType("application/json")
            ).andDo(print()).andExpect(status().isNotFound());
        }

        @Test
        void updateOwnContato() throws Exception {
            mockMvc.perform(put("/api/v1/contatos/me/1")
                    .content("{}")
                    .contentType("application/json")
            ).andDo(print()).andExpect(status().isNotFound());
        }
        

        @Test
        void deleteContato() throws Exception {
            mockMvc.perform(delete("/api/v1/contatos/1")).andDo(print()).andExpect(status().isOk());
        }
    }
}