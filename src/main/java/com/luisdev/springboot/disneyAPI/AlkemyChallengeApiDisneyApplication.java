package com.luisdev.springboot.disneyAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.luisdev.springboot.disneyAPI.models.service.IUploadService;


@SpringBootApplication
public class AlkemyChallengeApiDisneyApplication implements CommandLineRunner {
	
	@Autowired
	IUploadService uploadService;

	public static void main(String[] args) {
		SpringApplication.run(AlkemyChallengeApiDisneyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		uploadService.deleteAll();
		uploadService.init();
		
	}
	
	

}
