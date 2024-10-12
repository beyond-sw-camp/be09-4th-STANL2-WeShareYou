package stanl_2.weshareyou.domain.member.aggregate.vo.response.findlikeboard;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LikeNoResponseVO {

    private List<BoardLikesResponseVO> boardLikes;
}
