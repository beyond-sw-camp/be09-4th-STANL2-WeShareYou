package stanl_2.weshareyou.domain.member.aggregate.vo.response.findlikeboard;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BoardLikesResponseVO {
    private String title;
    private String content;
    private Integer commentCount;
    private Integer likesCount;
}
