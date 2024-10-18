package stanl_2.weshareyou.domain.product.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.weshareyou.domain.product.aggregate.entity.ProductStatus;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProductUpdateResponseVO {

    @NotNull
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String imageUrl;

    @NotNull
    private String category;

    @NotNull
    private ProductStatus status;

    private Timestamp startAt;

    private Timestamp endAt;

    @NotNull
    private Long adminId;
}
