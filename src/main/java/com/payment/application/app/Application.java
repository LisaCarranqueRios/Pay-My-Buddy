package com.payment.application.app;

import com.payment.application.dao.AccountDao;
import com.payment.application.dao.BankAccountDao;
import com.payment.application.dao.TransactionDao;
import com.payment.application.model.Account;
import com.payment.application.model.BankAccount;
import com.payment.application.model.Transaction;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Log4j2
@Configuration
@ComponentScan(basePackages = {"com.payment.application"})
@EnableAutoConfiguration
@EntityScan(basePackageClasses = {Account.class, Transaction.class, BankAccount.class})
@EnableJpaRepositories(basePackageClasses = {AccountDao.class, TransactionDao.class, BankAccountDao.class})
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

}

