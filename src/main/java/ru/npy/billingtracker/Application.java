package ru.npy.billingtracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.npy.billingtracker.account.Account;
import ru.npy.billingtracker.account.AccountRepository;

import java.math.BigDecimal;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Bean
//	public CommandLineRunner demo(AccountRepository accountRepository){
//		return args -> {
//			Account account = new Account("Test account", BigDecimal.valueOf(1000), "RUB");
//
//			System.out.println("Создаем аккаунт");
//			accountRepository.save(account);
//			System.out.println("Аккаунт успешно создан");
//		};
//	}
}
