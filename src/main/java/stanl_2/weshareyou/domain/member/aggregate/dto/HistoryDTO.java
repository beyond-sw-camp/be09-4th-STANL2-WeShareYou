package stanl_2.weshareyou.domain.member.aggregate.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HistoryDTO {

    Long id;

    String loginId;

    LocalDateTime loginDate;

    String clientIp;

    String userAgent;
}
