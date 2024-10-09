package stanl_2.weshareyou.global.security.service.smtp;


import jakarta.mail.MessagingException;

public interface MailService {

//    /* 6자리 난수 생성 */
//    String createCode();
//
//    /* Thymeleaf 기반의 html 파일에 값 넣고 연결하는 메소드 */
//    String setContext(String code);
//
//    MimeMessage createEmailForm(String email) throws MessagingException;

    /* 만든 메일 전송 */
    void sendEmail(String toEmail) throws MessagingException;


    // 보낸 이메일과 인증번호 일치하는지 확인
    Boolean verifyEmailCode(String email, String code);
}
