package stanl_2.weshareyou.domain.member.aggregate.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UpdateProfileRequestVO {

    @NotNull
    private String nickname;

    @NotNull
    private String profile_url;

    @NotNull
    private String introduction;

    @NotNull
    private String language;
}
