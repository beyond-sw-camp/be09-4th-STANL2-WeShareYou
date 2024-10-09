package stanl_2.weshareyou.domain.member.aggregate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name="MEMBER")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;
    @Column(name = "MEMBER_LOGIN_ID")
    private String loginId;
    @Column(name = "MEMBER_PASSWORD")
    private String password;
    @Column(name = "MEMBER_NAME")
    private String name;
    @Column(name = "MEMBER_AGE")
    private Integer age;
    @Column(name = "MEMBER_NATIONALITY")
    private String nationality;
    @Column(name = "MEMBER_SEX")
    private String sex;
    @Column(name = "MEMBER_PHONE")
    private String phone;
    @Column(name = "MEMBER_ROLE")
    private String role;
    @Column(name = "MEMBER_NICKNAME")
    private String nickname;
    @Column(name = "MEMBER_PROFILE_URL")
    private String profileUrl;
    @Column(name = "member_introduction")
    private String introduction;
    @Column(name = "member_language")
    private String language;
    @Column(name = "member_created_at")
    private String created_at;
    @Column(name = "member_updated_at")
    private String updated_at;
    @Column(name = "member_active")
    private Boolean active;

}
