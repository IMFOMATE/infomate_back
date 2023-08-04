package com.pro.infomate;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@SpringBootApplication
public class InfomateApplication {


	public static void main(String[] args) {
		SpringApplication.run(InfomateApplication.class, args);
	}


}
