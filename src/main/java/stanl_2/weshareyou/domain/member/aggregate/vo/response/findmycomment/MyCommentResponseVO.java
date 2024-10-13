package stanl_2.weshareyou.domain.member.aggregate.vo.response.findmycomment;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MyCommentResponseVO {
    private String content;
    private String createdAt;
    private String updatedAt;
}
