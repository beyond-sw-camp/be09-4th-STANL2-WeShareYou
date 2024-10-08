package stanl_2.weshareyou.domain.member.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.weshareyou.domain.member.aggregate.Role;
import stanl_2.weshareyou.domain.member.aggregate.Sex;

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
    private int age;

    @NotNull
    private String nationality;

    @NotNull
    private Sex sex;

    @NotNull
    private String phone;

    @NotNull
    private int point;

    @NotNull
    private Role role;

    @NotNull
    private String nickname;

    @NotNull
    private String profileUrl;

    @NotNull
    private String introduction;

    @NotNull
    private String language;
}
