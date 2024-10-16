package stanl_2.weshareyou.domain.member.aggregate.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CheckSmsCodeRequestVO {
    @NotNull(message = "인증할 연락처를 입력해주세요.")
    private String phone;

    @NotNull(message = "인증번호를 입력해주세요.")
    private String code;
}
