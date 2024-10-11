package stanl_2.weshareyou.domain.member.aggregate.dto;

import lombok.*;
import stanl_2.weshareyou.domain.board.aggregate.entity.Board;
import stanl_2.weshareyou.domain.board_like.aggregate.entity.BoardLike;
import stanl_2.weshareyou.domain.member.aggregate.Role;
import stanl_2.weshareyou.domain.member.aggregate.Sex;
import stanl_2.weshareyou.domain.member.aggregate.vo.response.findlikeboard.LikeNoResponseVO;
import stanl_2.weshareyou.domain.member.aggregate.vo.response.findmyboard.MyBoardResponseVO;

import java.util.ArrayList;
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
    private String createdAt;
    private String updatedAt;
    private Boolean active;

    private List<MyBoardResponseVO> board;

    private List<LikeNoResponseVO> boardLike;
}
