package stanl_2.weshareyou.domain.member.aggregate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.weshareyou.domain.member.aggregate.Role;
import stanl_2.weshareyou.domain.member.aggregate.Sex;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MEMBER_ID")
    private Long id;

    @Column(name="LOGIN_ID")
    @NotNull
    private String loginId;

    @Column(name="PASSWORD")
    @NotNull
    private String password;

    @Column(name="NAME")
    @NotNull
    private String name;

    @Column(name="AGE")
    @NotNull
    private int age;

    @Column(name="NATIONALITY")
    @NotNull
    private String nationality;

    @Column(name="SEX")
    @NotNull
    private Sex sex;

    @Column(name="PHONE")
    @NotNull
    private String phone;

    @Column(name="POINT")
    @NotNull
    private int point = 0;

    @Column(name="ROLE")
    @NotNull
    private Role role = Role.ROLE_MEMBER;

    @Column(name="NICKNAME")
    @NotNull
    private String nickname;

    @Column(name="PROFILE_URL")
    private String profileUrl;

    @Column(name="INTRODUCTION")
    private String introduction;

    @Column(name="LANGUAGE")
    @NotNull
    private String language;

    @Column(name="CREATED_AT")
    @NotNull
    private String createdAt;

    @Column(name="UPDATED_AT")
    @NotNull
    private String updatedAt;

    @Column(name="ACTIVE")
    @NotNull
    private Boolean active = true;
}
