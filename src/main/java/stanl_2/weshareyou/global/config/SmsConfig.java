package stanl_2.weshareyou.global.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SmsConfig {

    @Value("${spring.sms.api-key}")
    private String apiKey;

    @Value("${spring.sms.api-secret}")
    private String apiSecret;

    @Value("${spring.sms.provider}")
    private String smsProvider;

    @Value("${spring.sms.sender}")
    private static String smsSender;

    private DefaultMessageService messageService;

    @PostConstruct
    public void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(
                this.apiKey,
                this.apiSecret,
                this.smsProvider
        );
    }
}
