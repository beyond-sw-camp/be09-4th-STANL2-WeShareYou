package stanl_2.weshareyou.domain.member.aggregate.vo.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LoginResponseVO {
    private String status;
    private String jwtToken;
}
