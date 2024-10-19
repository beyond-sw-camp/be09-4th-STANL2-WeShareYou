package stanl_2.weshareyou.domain.notice.aggregate.vo.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NoticeCreateRequestVO {

    @NotNull
    @Size(min = 1, max = 255, message = "1자 이상 255자 이내로 입력해 주세요.")
    private String title;

    @NotNull
    @Size(min = 1, max = 1000, message = "1자 이상 1000자 이내로 입력해 주세요.")
    private String content;
}
