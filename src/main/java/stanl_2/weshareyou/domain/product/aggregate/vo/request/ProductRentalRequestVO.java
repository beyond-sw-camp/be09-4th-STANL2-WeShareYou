package stanl_2.weshareyou.domain.product.aggregate.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductRentalRequestVO {

    @NotNull
    private Long id;

    @NotNull
    private Long memberId;
}
