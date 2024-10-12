package stanl_2.weshareyou.domain.member.aggregate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.weshareyou.domain.board.aggregate.entity.Board;
import stanl_2.weshareyou.domain.board_comment.aggregate.entity.BoardComment;
import stanl_2.weshareyou.domain.board_like.aggregate.entity.BoardLike;
import stanl_2.weshareyou.domain.member.aggregate.Role;
import stanl_2.weshareyou.domain.member.aggregate.Sex;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name="MEMBER_LOGIN_ID")
    @NotNull
    private String loginId;

    @Column(name="MEMBER_PASSWORD")
    @NotNull
    private String password;

    @Column(name="MEMBER_NAME")
    @NotNull
    private String name;

    @Column(name="MEMBER_AGE")
    @NotNull
    private Integer age;

    @Column(name="MEMBER_NATIONALITY")
    @NotNull
    private String nationality;

    @Column(name="MEMBER_SEX")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Sex sex;

    @Column(name="MEMBER_PHONE")
    @NotNull
    private String phone;

    @Column(name="MEMBER_POINT")
    @NotNull
    private Integer point = 0;

    @Column(name="MEMBER_ROLE")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role = Role.ROLE_MEMBER;

    @Column(name="MEMBER_NICKNAME")
    @NotNull
    private String nickname;

    @Column(name="MEMBER_PROFILE_URL")
    private String profileUrl;

    @Column(name="MEMBER_INTRODUCTION")
    private String introduction;

    @Column(name="MEMBER_LANGUAGE")
    @NotNull
    private String language;

    @Column(name="MEMBER_CREATED_AT")
    @NotNull
    private String createdAt;

    @Column(name="MEMBER_UPDATED_AT")
    @NotNull
    private String updatedAt;

    @Column(name="MEMBER_ACTIVE")
    @NotNull
    private Boolean active = true;

    @OneToMany(mappedBy = "member")
    private List<Board> board;

    @OneToMany(mappedBy = "member")
    private List<BoardLike> boardlike;

    @OneToMany(mappedBy = "member")
    private List<BoardComment> boardComment;
}
