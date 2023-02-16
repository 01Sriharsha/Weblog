package dev.sriharsha.WeBlog.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiDocConfig {

    @Bean
    public OpenAPI openApiConfig() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI().info(getInfo())
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(getComponents());
    }

    private static Components getComponents() {
        return new Components().addSecuritySchemes(
                "bearerAuth", new SecurityScheme()
                        .name("bearerAuth")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
        );
    }

    private static Info getInfo() {
        return new Info()
                .title("Weblog Application")
                .description("This is a Online Blog Application developed using Spring Boot")
                .version("1.0")
                .license(new License()
                        .name("Apache 2.0")
                        .url("https://www.apache.org/licenses/LICENSE-2.0"))
                .contact(new Contact()
                        .name("K Sriharsha")
                        .url("https://ksriharsha.vercel.app/")
                        .email("sriharshak200@gmail.com"))
                .termsOfService("http://swagger.io/terms/");
    }

}
