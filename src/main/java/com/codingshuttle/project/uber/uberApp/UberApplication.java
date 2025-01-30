package com.codingshuttle.project.uber.uberApp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UberApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(UberApplication.class, args);



	}
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Executing additional startup logic in CommandLineRunner!");
	}

}
