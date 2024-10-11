package stanl_2.weshareyou.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "WeShareYou API Specification",
                description = "Specification for buds",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {
}
