package stanl_2.weshareyou.domain.product.aggregate.dto;

import lombok.*;
import stanl_2.weshareyou.domain.product.aggregate.entity.ProductCategory;
import stanl_2.weshareyou.domain.product.aggregate.entity.ProductStatus;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private ProductCategory category;
    private ProductStatus status;
    private Timestamp startAt;
    private Timestamp endAt;
    private Boolean rental;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Long adminId;
    private Long memberId;
}
