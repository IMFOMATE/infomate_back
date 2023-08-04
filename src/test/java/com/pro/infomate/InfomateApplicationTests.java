package com.pro.infomate;

import com.pro.infomate.QMember;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
class InfomateApplicationTests {

	@Autowired
	EntityManager em;

	@Test
	@Transactional
	@Commit
	void dbtest() {



	}

	@Test
	void contextLoads() {
	}

}
