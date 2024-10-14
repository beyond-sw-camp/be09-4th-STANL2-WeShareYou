package stanl_2.weshareyou.domain.product.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProductReadCategoryResponseVO {

    @NotNull
    private Long cursorId;

    @NotNull
    private List<ProductSummaryVO> contents;

    @NotNull
    private Boolean hasNext;

}
