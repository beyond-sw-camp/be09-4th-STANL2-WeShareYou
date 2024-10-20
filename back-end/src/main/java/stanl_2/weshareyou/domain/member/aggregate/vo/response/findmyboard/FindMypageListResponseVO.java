package stanl_2.weshareyou.domain.member.aggregate.vo.response.findmyboard;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FindMypageListResponseVO {
    private String nickname;

    private List<MyBoardResponseVO> board;
}
