package ru.npy.billingtracker.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.npy.billingtracker.exceptions.handlers.GlobalExceptionHandler;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@Import(GlobalExceptionHandler.class)
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private AccountRepository accountRepository;

    @Test
    void createAccountReturnCreatedAccountTest() throws Exception {
        Account account = new Account("source account", BigDecimal.valueOf(1000), "RUB");
        Account createdAccount = new Account("source account", BigDecimal.valueOf(1000), "RUB");

        when(accountRepository.save(any(Account.class))).thenReturn(createdAccount);

        mockMvc.perform(post("/api/accounts/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(account)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("source account"))
                .andExpect(jsonPath("$.balance").value(1000));
    }

    @Test
    void getAccountReturnAccountTest() throws Exception {
        UUID id = UUID.randomUUID();
        Account account = new Account("get account", BigDecimal.valueOf(5000), "USD");

        when(accountRepository.findById(any(UUID.class))).thenReturn(Optional.of(account));

        mockMvc.perform(get("/api/accounts/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("get account"));
    }

    @Test
    void getAccountNotFoundAccauntTest() throws Exception {
        UUID id = UUID.randomUUID();

        when(accountRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/accounts/{id}", id));
    }
}
