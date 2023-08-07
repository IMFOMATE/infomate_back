package com.pro.infomate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.persistence.EntityManager;

@SpringBootTest
class InfomateApplicationTests {

	@Autowired
	EntityManager em;


	@Test
	void contextLoads() {
	}

}
