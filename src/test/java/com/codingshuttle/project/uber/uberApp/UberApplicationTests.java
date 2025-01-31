package com.codingshuttle.project.uber.uberApp;

import com.codingshuttle.project.uber.uberApp.services.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UberApplicationTests {
	@Autowired
    private EmailSenderService emailSenderService;
	@Test
	void contextLoads() {
		emailSenderService.sendEmail("akshayanand7654@gmail.com","This is the testing email","Body of my  mail");
	}

}
