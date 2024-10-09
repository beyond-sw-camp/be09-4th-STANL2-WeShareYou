package stanl_2.weshareyou.domain.notice.aggregate.dto;

import lombok.*;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NoticeDTO {
    private Long id;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
    private Boolean active;
    private Long adminId;
}
