package com.example.bookrental;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

@OpenAPIDefinition(info = @Info(title = "Book rental API", version = "1.0", description = "Allows the admin and librarian too keep track of the rented books and Transactions associated with it.")
,servers = @Server(url = "https://bookrental-demo-production.up.railway.app/", description = "Default Server URL")
)
@SecurityScheme(name = "bookRental", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class BookRentalApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookRentalApplication.class, args);
    }

}
