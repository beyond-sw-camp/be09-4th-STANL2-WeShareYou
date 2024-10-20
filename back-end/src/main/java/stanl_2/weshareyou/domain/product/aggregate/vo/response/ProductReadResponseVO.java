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
public class ProductReadResponseVO {

    @NotNull
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    private String imageUrl;

    @NotNull
    private String category;

    @NotNull
    private ProductStatus status;

    @NotNull
    private Timestamp startAt;

    @NotNull
    private Timestamp endAt;

    @NotNull
    private Boolean rental;

    @NotNull
    private Timestamp createdAt;

    @NotNull
    private Timestamp updatedAt;

    @NotNull
    private Long adminId;

    private Long memberId;
}
