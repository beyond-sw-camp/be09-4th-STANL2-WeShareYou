package stanl_2.weshareyou.domain.product.aggregate.vo.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import stanl_2.weshareyou.domain.product.aggregate.entity.ProductCategory;
import stanl_2.weshareyou.domain.product.aggregate.entity.ProductStatus;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductUpdateRequestVO {

    @NotNull
    private Long id;

    @NotNull(message = "수정하실 게시글의 제목을 입력해 주세요.")
    private String title;

    @NotNull
    @Size(min = 1, max = 1000, message = "1000자 이내로 입력해 주세요.")
    private String content;

    @NotNull(message = "카테고리를 선택해 주세요.")
    private ProductCategory category;

    @NotNull(message = "물품상태를 선택해 주세요.")
    private ProductStatus status;

    private Timestamp startAt;

    private Timestamp endAt;
}
