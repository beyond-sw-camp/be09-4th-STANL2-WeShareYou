package stanl_2.weshareyou.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import stanl_2.weshareyou.domain.chat.dateConverter.DateToTimestampConverter;
import stanl_2.weshareyou.domain.chat.dateConverter.TimestampToDateConverter;

import java.util.Arrays;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new TimestampToDateConverter(),
                new DateToTimestampConverter()
        ));
    }
}