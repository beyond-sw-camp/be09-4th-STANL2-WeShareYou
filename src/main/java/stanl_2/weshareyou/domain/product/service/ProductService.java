package stanl_2.weshareyou.domain.product.service;

import org.springframework.web.multipart.MultipartFile;
import stanl_2.weshareyou.domain.product.aggregate.dto.ProductDTO;
import stanl_2.weshareyou.global.common.dto.CursorDTO;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productRequestDTO, MultipartFile imageUrl);

    ProductDTO updateProduct(ProductDTO productRequestDTO);

    ProductDTO deleteProduct(ProductDTO productRequestDTO);

//    CursorDTO readAllProductList(CursorDTO cursorDTO);

    ProductDTO readProduct(Long productId);

    CursorDTO readProductByCategory(CursorDTO cursorDTO);

    ProductDTO updateRentalProduct(ProductDTO productDTO);

    ProductDTO updateRentalApproveProduct(ProductDTO productDTO);

    ProductDTO updateRentalReturnProduct(ProductDTO productDTO);
}
