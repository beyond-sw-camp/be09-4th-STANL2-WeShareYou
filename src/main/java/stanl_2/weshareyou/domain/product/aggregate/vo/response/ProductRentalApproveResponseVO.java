package stanl_2.weshareyou.domain.product.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductRentalApproveResponseVO {

    @NotNull
    private Long id;

    @NotNull
    private Boolean rental;

    @NotNull
    private Long memberId;
}
