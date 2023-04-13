package com.badalov.sms.badalovsms;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(servers = @Server(url = "/"), info = @Info(title = "SMS Service"))
public class BadalovSmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BadalovSmsApplication.class, args);
	}

}
