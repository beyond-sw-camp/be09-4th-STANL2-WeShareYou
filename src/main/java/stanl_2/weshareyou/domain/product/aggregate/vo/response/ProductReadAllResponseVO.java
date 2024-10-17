package stanl_2.weshareyou.domain.product.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.weshareyou.domain.product.aggregate.entity.ProductCategory;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProductReadAllResponseVO {

    @NotNull
    private ProductCategory category;

    @NotNull
    private Long cursorId;

    @NotNull
    private List<ProductSummaryVO> contents;

    @NotNull
    private boolean hasNext;

}
