package stanl_2.weshareyou.domain.product.aggregate.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.weshareyou.domain.product.aggregate.entity.ProuctCategory;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductUpdateRequestVO {

    @NonNull
    private Long id;

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

    @NonNull
    private Long adminId;
}
