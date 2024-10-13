package stanl_2.weshareyou.global.security.service.sms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import stanl_2.weshareyou.global.security.service.redis.RedisService;

@Slf4j
@Service(value = "SmsService")
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService{
    private final RedisService redisService;

    public Boolean verifySmsCode(String phone, String code) {
        String codeFoundBySms = redisService.getData(phone);
        if (codeFoundBySms == null) {
            return false;
        }
        return codeFoundBySms.equals(code);
    }
}
