package com.ida.tutorial.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

  @Value("${ida.openapi.dev-url}")
  private String devUrl;

  @Bean
  public OpenAPI myOpenAPI() {
    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Server URL in Development environment");
    Contact contact = new Contact();

    Info info = new Info()
        .title("Api de gestion des tutoriels")
        .version("1.0")
        .contact(contact)
        .description("Api de gestion des tutoriels");

    return new OpenAPI().info(info).servers(List.of(devServer));
  }
}
