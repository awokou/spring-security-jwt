package com.server.spring.security.jwt;

import com.server.spring.security.jwt.entity.Role;
import com.server.spring.security.jwt.entity.User;
import com.server.spring.security.jwt.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableCaching
@SpringBootApplication
public class SpringSecurityJwtApplication {

	private static final Logger log = LoggerFactory.getLogger(SpringSecurityJwtApplication.class);

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

    public SpringSecurityJwtApplication(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public static void main(String[] args) {

		SpringApplication.run(SpringSecurityJwtApplication.class, args);

		log.info("*******************************************************");
		log.info("SPRING SECURITY JWT");
		log.info("*******************************************************");
	}

	@Bean
	CommandLineRunner commandLineRunner() {
		return args -> {
			User user = User.builder()
					.firstName("Mathieu")
					.lastName("Awokou")
					.email("admin@gmail.com")
					.password("password")
					.role(Role.ADMIN)
					.build();

			if (!userRepository.existsByEmail("admin@gmail.com")) {
				user.setPassword(passwordEncoder.encode("password"));
				userRepository.save(user);
				log.info("Admin user created successfully");
			} else {
				log.info("Admin user already exists");
			}
		};
	}
}
