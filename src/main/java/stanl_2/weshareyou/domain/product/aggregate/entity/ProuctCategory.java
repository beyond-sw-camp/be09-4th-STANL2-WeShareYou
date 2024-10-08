package stanl_2.weshareyou.domain.product.aggregate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ProuctCategory {
    NECESSITIES,
    CLOTHES,
    KITCHENWARES,
    TOY,
    DEVICE,
    ETC
}
