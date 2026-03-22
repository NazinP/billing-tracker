package ru.npy.billingtracker.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TestEntityManager manager;

    @Test
    void save_PersistAccount(){
        Account account = new Account("Persist Test", BigDecimal.valueOf(1000), "RUB");

        Account saved = accountRepository.save(account);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Persist Test");
        assertThat(saved.getBalance()).isEqualByComparingTo(BigDecimal.valueOf(1000));

        Account found = manager.find(Account.class, saved.getId());
        assertThat(found).isNotNull();
    }

    @Test
    void getBYId_WhenExists_ShouldReturnAccount(){
        Account account = new Account("Exists Test", BigDecimal.valueOf(1000), "RUB");
        Account saved = manager.persist(account);
        manager.flush();

        Optional<Account> found = accountRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Exists Test");
    }

    @Test
    void getBYId_WhenNotExists_ShouldReturnEmpty(){
        UUID notExistentId = UUID.randomUUID();

        Optional<Account> found = accountRepository.findById(notExistentId);

        assertThat(found).isEmpty();
    }

    @Test
    void existsByName_WhenAccountExists_ShoulReturnTrue(){
        Account account = new Account("Unique Name", BigDecimal.valueOf(1000), "RUB");

        manager.persist(account);
        manager.flush();

        boolean exists = accountRepository.existsByName("Unique Name");

        assertThat(exists).isTrue();
    }
}
