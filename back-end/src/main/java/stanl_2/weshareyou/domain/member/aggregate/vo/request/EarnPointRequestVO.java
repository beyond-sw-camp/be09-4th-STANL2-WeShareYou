package stanl_2.weshareyou.domain.member.aggregate.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EarnPointRequestVO {
    @NotNull(message = "적립한 포인트를 입력해주세요.")
    private Integer point;
}
