package com.akobir.wsm;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "Warehouse-Sales-Management API", version = "1.0.0"),
        servers = {@Server(url = "http://localhost:8082")}
)
@SecurityScheme(name = "Token", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT",
        description = "Bearer token for security.")
public class WsmApplication {

    public static void main(String[] args) {
        SpringApplication.run(WsmApplication.class, args);
    }

}
