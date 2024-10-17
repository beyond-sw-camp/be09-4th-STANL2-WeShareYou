package stanl_2.weshareyou.domain.member.aggregate.vo.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SendEmailRequestVO {

    @NotNull(message = "전송할 이메일을 입력해주세요.")
    @Email
    private String email;
}
