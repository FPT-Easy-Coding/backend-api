package com.quizztoast.backendAPI.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
//doc api http://localhost:8080/swagger-ui/index.html#/
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Ha Viet Hieu",
                        email = "hieuhvhe176256@fpt.edu.vn",
                        url = "https://www.facebook.com/haviethieu8888.jsclub/"
                ),
                description = "OpenApi documentation for Spring Security",
                title = "OpenApi specification - Ha Viet Hieu",
                version = "1.0",
                license = @License(
                        name = "License name",
                        url = "https://some-url"
                ),
                termsOfService = "Term of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "http://updating..."
                )
        },
        security = {
            @SecurityRequirement(
                    name = "bearerAuth"
            )
        }

)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {
}
