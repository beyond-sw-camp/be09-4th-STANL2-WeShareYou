package stanl_2.weshareyou.domain.member.aggregate.vo.response;

import lombok.*;
import stanl_2.weshareyou.domain.board.aggregate.dto.BoardDTO;
import stanl_2.weshareyou.domain.member.aggregate.vo.BoardResponseVO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FindMypageListResponseVO {
    private String nickname;

    private List<BoardResponseVO> board;
}
