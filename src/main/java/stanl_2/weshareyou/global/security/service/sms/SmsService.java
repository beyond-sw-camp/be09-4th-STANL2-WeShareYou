package stanl_2.weshareyou.global.security.service.sms;

public interface SmsService {

    Boolean verifySmsCode(String email, String code);
}
