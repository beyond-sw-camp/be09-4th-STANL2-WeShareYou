package stanl_2.weshareyou.global.security.constants;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApplicationConstants {

    @Value("${JWT.SECRET_KEY}")
    private String JWT_SECRET_KEY;

    @Value("${JWT.JWT_SECRET_DEFAULT_VALUE}")
    private String JWT_SECRET_DEFAULT_VALUE;

    @Value("${JWT.JWT_HEADER}")
    private String JWT_HEADER;
}
