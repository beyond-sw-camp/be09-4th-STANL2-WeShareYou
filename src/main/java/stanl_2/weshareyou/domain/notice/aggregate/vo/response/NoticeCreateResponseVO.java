package stanl_2.weshareyou.domain.notice.aggregate.vo.response;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NoticeCreateResponseVO {
    @NotNull
    private Long id;

    @NotNull
    private String title;
}
