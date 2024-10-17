package stanl_2.weshareyou.domain.product.service;

import stanl_2.weshareyou.domain.product.aggregate.dto.ProductDTO;
import stanl_2.weshareyou.global.common.dto.CursorDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productRequestDTO);

    ProductDTO updateProduct(ProductDTO productRequestDTO);

    ProductDTO deleteProduct(ProductDTO productRequestDTO);

//    CursorDTO readAllProductList(CursorDTO cursorDTO);

    ProductDTO readProduct(Long productId);

    CursorDTO readProductByCategory(CursorDTO cursorDTO);

    ProductDTO updateRentalProduct(ProductDTO productDTO);

    ProductDTO updateRentalApproveProduct(ProductDTO productDTO);

    ProductDTO updateRentalReturnProduct(ProductDTO productDTO);
}
