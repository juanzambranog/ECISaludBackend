package eci.cvds.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ECI Salud Vital API")
                        .version("1.0")
                        .description("API for managing medical appointments at ECI Salud Vital")
                        .contact(new Contact()
                                .name("ECI Salud Vital")
                                .email("support@ecisalud.com")));
    }
}
