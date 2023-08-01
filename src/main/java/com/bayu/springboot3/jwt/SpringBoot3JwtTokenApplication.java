package com.bayu.springboot3.jwt;

import com.bayu.springboot3.jwt.dto.RegisterRequest;
import com.bayu.springboot3.jwt.model.Role;
import com.bayu.springboot3.jwt.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBoot3JwtTokenApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot3JwtTokenApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService authenticationService
	) {
		return args -> {
			RegisterRequest admin = RegisterRequest.builder()
					.firstName("Admin")
					.lastName("Admin")
					.email("admin@mail.com")
					.password("password")
					.role(Role.ADMIN)
					.build();
			System.out.println("Admin token: " + authenticationService.register(admin).getAccessToken());

			RegisterRequest manager = RegisterRequest.builder()
					.firstName("Admin")
					.lastName("Admin")
					.email("manager@mail.com")
					.password("password")
					.role(Role.MANAGER)
					.build();
			System.out.println("Manager token: " + authenticationService.register(admin).getAccessToken());
		};
	}

}
