package stanl_2.weshareyou.domain.product.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.weshareyou.domain.product.aggregate.entity.ProuctCategory;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductCreateResponseVO {

    @NotNull
    private String title;

    @NotNull
    private String content;

    private String imageUrl;

    @NotNull
    private ProuctCategory category;

    @NotNull
    private String startAt;;

    @NotNull
    private String endAt;

    @NotNull
    private Long adminId;
}
