package stanl_2.weshareyou.domain.product.service;

import stanl_2.weshareyou.domain.product.aggregate.dto.ProductDTO;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productRequestDTO);

}
