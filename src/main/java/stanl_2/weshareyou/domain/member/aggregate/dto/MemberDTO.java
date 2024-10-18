package stanl_2.weshareyou.domain.member.aggregate.dto;

import lombok.*;
import stanl_2.weshareyou.domain.member.aggregate.Role;
import stanl_2.weshareyou.domain.member.aggregate.Sex;
import stanl_2.weshareyou.domain.member.aggregate.vo.response.findlikeboard.LikeNoResponseVO;
import stanl_2.weshareyou.domain.member.aggregate.vo.response.findmyboard.MyBoardResponseVO;
import stanl_2.weshareyou.domain.member.aggregate.vo.response.findmycomment.MyCommentResponseVO;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
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
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Boolean active;

    private List<MyBoardResponseVO> board;

    private List<LikeNoResponseVO> boardLike;

    private List<MyCommentResponseVO> boardComment;
}
