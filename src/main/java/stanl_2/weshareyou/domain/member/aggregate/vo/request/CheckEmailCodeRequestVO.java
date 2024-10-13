package stanl_2.weshareyou.domain.member.aggregate.vo.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CheckEmailCodeRequestVO {
    @NotNull(message = "인증할 이메일을 입력해주세요.")
    @Email
    private String email;

    @NotNull(message = "인증번호를 입력해주세요.")
    private String code;
}
