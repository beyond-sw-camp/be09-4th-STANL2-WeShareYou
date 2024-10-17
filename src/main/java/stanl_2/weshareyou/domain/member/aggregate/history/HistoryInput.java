package stanl_2.weshareyou.domain.member.aggregate.history;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class HistoryInput {
    private String userId;
    private String userAgent;
    private String clientIp;
}