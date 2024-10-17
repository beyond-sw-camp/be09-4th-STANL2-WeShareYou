package stanl_2.weshareyou.domain.member.aggregate.history;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

}