package stanl_2.weshareyou.domain.product.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.weshareyou.domain.product.aggregate.entity.ProductStatus;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProductSummaryVO {

    @NotNull
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String imageUrl;

    @NotNull
    private Boolean rental;

    @NotNull
    private ProductStatus status;
}
