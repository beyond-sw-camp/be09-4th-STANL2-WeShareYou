package stanl_2.weshareyou.domain.member.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RegisterResponseVO {

    @NotNull
    private String loginId;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private Integer age;

    @NotNull
    private String nationality;

    @NotNull
    private String sex;

    @NotNull
    private String phone;

    @NotNull
    private Integer point;

    @NotNull
    private String role;

    @NotNull
    private String nickname;

    @NotNull
    private String profileUrl;

    @NotNull
    private String introduction;

    @NotNull
    private String language;
}
