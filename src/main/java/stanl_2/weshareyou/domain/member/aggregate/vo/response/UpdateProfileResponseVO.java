package stanl_2.weshareyou.domain.member.aggregate.vo.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import stanl_2.weshareyou.domain.member.aggregate.Role;
import stanl_2.weshareyou.domain.member.aggregate.Sex;

public class UpdateProfileResponseVO {
    @NotNull
    private String loginId;

    @NotNull
    private String name;

    @NotNull
    private Integer age;

    @NotNull
    private String nationality;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @NotNull
    private String phone;

    @NotNull
    private Integer point;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    private String nickname;

    @NotNull
    private String introduction;

    @NotNull
    private String language;

    @NotNull
    private String createdAt;

    @NotNull
    private String updatedAt;
}
