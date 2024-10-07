package stanl_2.weshareyou.domain.product.aggregate.dto;

import lombok.*;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
import stanl_2.weshareyou.domain.product.aggregate.entity.ProuctCategory;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductDTO {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private ProuctCategory category;
    private String startAt;
    private String endAt;
    private boolean rental;
    private String createdAt;
    private String updatedAt;
    private Long adminId;
    private Member memberId;
}
