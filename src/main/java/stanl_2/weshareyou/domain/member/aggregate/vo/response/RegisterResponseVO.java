package stanl_2.weshareyou.domain.member.aggregate.vo.response;

import lombok.*;
import stanl_2.weshareyou.domain.member.aggregate.Role;
import stanl_2.weshareyou.domain.member.aggregate.Sex;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RegisterResponseVO {

    private String loginId;

    private String password;

    private String name;

    private int age;

    private String nationality;

    private Sex sex;

    private String phone;

    private int point;

    private Role role;

    private String nickname;

    private String profileUrl;

    private String introduction;

    private String language;
}
