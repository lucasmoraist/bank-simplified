package com.lucasmoraist.bank_simplified.infra.doc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI documentation() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bank Simplified API")
                        .description("API for a simplified bank system")
                        .summary("API for a simplified bank system")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Lucas de Morais Nascimento Taguchi")
                                .email("luksmnt1101@gmail.com")
                                .url("https://www.linkedin.com/in/lucas-morais-152672219/")
                        )
                );
    }

}
