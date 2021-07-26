package com.payment.application.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:test.properties")
@EnableTransactionManagement
@Log4j2
public class DataBaseConfig {
}
