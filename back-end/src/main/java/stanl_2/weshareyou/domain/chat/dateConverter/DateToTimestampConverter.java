package stanl_2.weshareyou.domain.chat.dateConverter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class DateToTimestampConverter implements Converter<Date, Timestamp> {
    @Override
    public Timestamp convert(Date source) {
        return new Timestamp(source.getTime());
    }
}
