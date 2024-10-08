package stanl_2.weshareyou.domain.product.aggregate.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
import stanl_2.weshareyou.domain.product.aggregate.entity.ProuctCategory;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductCreateRequestVO {

    @NonNull
    private String title;

    @NonNull
    private String content;

    @NonNull
    private ProuctCategory category;

    @NonNull
    private String startAt;

    @NonNull
    private String endAt;

    @NotNull
    private Long adminId;
}
