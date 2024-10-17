package stanl_2.weshareyou.domain.member.aggregate.vo.response.findmycomment;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FindMyCommentListResponseVO {
    private String nickname;

    private List<MyCommentResponseVO> boardComment;
}
