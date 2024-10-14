package stanl_2.weshareyou.domain.member.aggregate.vo.response.findmyboard;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MyBoardResponseVO {

    private String title;
    private String content;
    private Integer commentCount;
    private Integer likesCount;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
