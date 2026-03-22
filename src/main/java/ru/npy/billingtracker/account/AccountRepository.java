package ru.npy.billingtracker.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    /**
     * Поиск аккаунта по имени
     * @param name
     * @return
     */
    Optional<Account> findByName(String name);

    /**
     * Поиск всех активных аккаунтов
     * TODO подумать насчет имени метода
     * @return
     */
    List<Account> findByActiveTrue();

    /**
     * Поиск всех активных аккаунтов по валюте
     * @param currency
     * @return
     */
    @Query("SELECT a FROM Account a WHERE a.currency = :currency AND a.active = true")
    List<Account> findActiveByCurrency(@Param("currency") String currency);

    /**
     * Проверка существования аккаунта по имени
     * @param name
     * @return
     */
    boolean existsByName(String name);
}
