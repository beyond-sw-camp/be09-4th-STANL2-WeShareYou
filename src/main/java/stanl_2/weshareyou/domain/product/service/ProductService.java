package stanl_2.weshareyou.domain.product.service;

import stanl_2.weshareyou.domain.product.aggregate.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productRequestDTO);

    ProductDTO updateProduct(ProductDTO productRequestDTO);

    ProductDTO deleteProduct(ProductDTO productRequestDTO);

    List<ProductDTO> readAllProductList();

    ProductDTO readProduct(Long productId);

    List<ProductDTO> readProductByCategory(String category);

    ProductDTO updateRentalProduct(ProductDTO productRequestDTO);
}
