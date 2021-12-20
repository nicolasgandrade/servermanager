package com.nicolasgandrade.servermanager;

import com.nicolasgandrade.servermanager.enums.Status;
import com.nicolasgandrade.servermanager.model.Server;
import com.nicolasgandrade.servermanager.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServermanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServermanagerApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ServerRepository serverRepository) {
		return args -> {
			serverRepository.save(new Server(
					null,
					"192.168.1.162",
					"Fedora Linux",
					"8GB",
					"Personal computer",
					"http://localhost:8080/servers/images/s1.png",
					Status.SERVER_UP));

			serverRepository.save(new Server(
					null,
					"192.164.2.156",
					"AWS",
					"8GB",
					"Remote DB computer",
					"http://localhost:8080/servers/images/s2.png",
					Status.SERVER_UP));

			serverRepository.save(new Server(
					null,
					"192.162.1.160",
					"Ubuntu Linux",
					"32GB",
					"Remote Server",
					"http://localhost:8080/servers/images/s3.png",
					Status.SERVER_UP));
		};
	}
}
