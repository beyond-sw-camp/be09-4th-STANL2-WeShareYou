package stanl_2.weshareyou.domain.member.aggregate.vo.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LoginRequestVO {
    private String loginId;

    private String password;
}
