package stanl_2.weshareyou.domain.member.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LoginResponseVO {
    @NotNull
    private String status;

    @NotNull
    private String jwtToken;
}
