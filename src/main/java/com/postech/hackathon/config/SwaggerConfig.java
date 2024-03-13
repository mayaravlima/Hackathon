package com.postech.hackathon.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        Contact contact = new Contact();
        contact.setEmail("may.veloso.lima@gmail.com");
        contact.setName("Mayara Lima");

        Info info = new Info()
                .title("Hackathon API")
                .version("1.0")
                .contact(contact)
                .description("Description");

        return new OpenAPI().info(info);
    }
}
