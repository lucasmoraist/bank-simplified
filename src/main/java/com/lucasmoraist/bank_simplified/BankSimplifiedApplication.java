package com.lucasmoraist.bank_simplified;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BankSimplifiedApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankSimplifiedApplication.class, args);
	}

}
