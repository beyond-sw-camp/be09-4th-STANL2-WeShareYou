package stanl_2.weshareyou.domain.member.aggregate.dto.reponse;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stanl_2.weshareyou.domain.member.aggregate.Role;
import stanl_2.weshareyou.domain.member.aggregate.Sex;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class reponseMemberDetailDTO {
    private Long id;
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
    private String createdAt;
    private String updatedAt;
    private Boolean active;
}
