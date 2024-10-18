package stanl_2.weshareyou.domain.member.aggregate.history;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@Table(name="LOGIN_HISTORY")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="LOGIN_HISTORY_ID")
    Long id;

    @Column(name="LOGIN_HISTORY_LOGIN_ID")
    String loginId;

    @Column(name="LOGIN_HISTORY_LOGIN_DATE")
    LocalDateTime loginDate;

    @Column(name="LOGIN_HISTORY_CLIENT_IP")
    String clientIp;

    @Column(name="LOGIN_HISTORY_USER_AGENT")
    String userAgent;

    // 생성자
    public LoginHistory(Long id, String loginDateStr, String clientIp, String loginId, String userAgent) {
        this.id = id;
        this.loginDate = parseDate(loginDateStr); // String을 LocalDateTime으로 변환
        this.clientIp = clientIp;
        this.loginId = loginId;
        this.userAgent = userAgent;
    }



    // 날짜 변환 함수
    private LocalDateTime parseDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        return LocalDateTime.parse(dateStr, formatter);
    }

}