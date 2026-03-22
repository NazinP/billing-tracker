package ru.npy.billingtracker.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    AccountRepository accountRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@RequestBody Account account){
        return accountRepository.save(account);
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable UUID id) throws AccountNotFoundException {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id.toString()));
    }
}
