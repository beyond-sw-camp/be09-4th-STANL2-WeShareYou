package stanl_2.weshareyou.domain.member.aggregate.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BoardResponseVO {

    private String title;
    private String content;
    private Integer commentCount;
    private Integer likesCount;
}
