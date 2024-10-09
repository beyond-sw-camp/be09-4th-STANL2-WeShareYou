package stanl_2.weshareyou.domain.product.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductReadCategoryResponseVO {

    @NotNull
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String imageUrl;

    @NotNull
    private String category;

    @NotNull
    private Boolean rental;

}
