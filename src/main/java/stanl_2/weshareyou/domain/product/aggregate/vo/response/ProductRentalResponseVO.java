package stanl_2.weshareyou.domain.product.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductRentalResponseVO {

    @NotNull
    private Long id;

    @NotNull
    private Long memberId;

    @NotNull
    private Boolean rental;
}
