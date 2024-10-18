package stanl_2.weshareyou.domain.member.aggregate.vo.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.weshareyou.domain.member.aggregate.Sex;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RegisterRequestVO {

    @NotNull(message = "아이디를 입력해주세요.")
    @Email
    private String loginId;

    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotNull(message = "성함을 입력해주세요.")
    private String name;

    @NotNull(message = "나이를 입력해주세요.")
    @Min(1)
    @Max(200)
    private Integer age;

    @NotNull(message = "국적을 입력해주세요.")
    private String nationality;

    @NotNull(message = "성별을 선택해주세요.")
    private Sex sex;

    @NotNull(message = "전화번호를 입력해주세요.")
    private String phone;

    @NotNull(message = "별칭을 입력해주세요.")
    private String nickname;

    private String profileUrl;

    private String introduction;

    @NotNull(message = "사용언어를 입력해주세요.")
    private String language;
}
