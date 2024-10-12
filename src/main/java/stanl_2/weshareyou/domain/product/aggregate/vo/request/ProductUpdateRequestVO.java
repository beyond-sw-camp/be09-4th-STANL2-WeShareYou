package stanl_2.weshareyou.domain.product.aggregate.vo.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import stanl_2.weshareyou.domain.product.aggregate.entity.ProductCategory;

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

    @NotNull
    private String imageUrl;

    @NotNull(message = "카테고리를 선택해 주세요.")
    private ProductCategory category;

    @NotNull(message = "대여 시작 날짜를 선택해 주세요.")
    private String startAt;

    @NotNull(message = "대여 종료 날짜를 선택해 주세요.")
    private String endAt;
}
