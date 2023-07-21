package artico.personaspot.configuration;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.servers.*;
import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.*;

@Configuration
@OpenAPIDefinition(servers = {
        @Server(url = "http://localhost:8080/", description = "Localhost Server"),
        @Server(url = "https://personaspot-production.up.railway.app/", description = "Prod Server"),
})
public class SwaggerConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(info());
    }

    private Info info() {
        return new Info()
                .title("Persona Spot")
                .description("API for managing person and contact records")
                .version("V0")
                .contact(new Contact()
                        .email("jeanartico13@hotmail.com")
                        .name("Jean Artico")
                        .url("https://github.com/HokkyokuArt")
                );
    }
}
