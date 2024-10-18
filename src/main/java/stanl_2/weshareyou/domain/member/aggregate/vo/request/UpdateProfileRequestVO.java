package stanl_2.weshareyou.domain.member.aggregate.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UpdateProfileRequestVO {

    @NotNull(message = "수정한 별명을 입력해주세요.")
    private String nickname;

    @NotNull(message = "수정한 자기소개를 입력해주세요.")
    private String introduction;

    @NotNull(message = "수정할 언어를 입력해주세요.")
    private String language;
}
