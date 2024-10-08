package stanl_2.weshareyou.domain.product.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stanl_2.weshareyou.domain.product.aggregate.dto.ProductDTO;
import stanl_2.weshareyou.domain.product.aggregate.vo.request.ProductCreateRequestVO;
import stanl_2.weshareyou.domain.product.aggregate.vo.response.ProductCreateResponseVO;
import stanl_2.weshareyou.domain.product.service.ProductService;
import stanl_2.weshareyou.global.common.response.ApiResponse;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ModelMapper modelMapper;
    private final ProductService productService;

    @Autowired
    public ProductController(ModelMapper modelMapper, ProductService productService) {
        this.modelMapper = modelMapper;
        this.productService = productService;
    }

    /**
     * 내용: 공유물품 등록
     * req:
     * {
     *     "title": "스탠리 텀블러",
     *     "content": "스탠리 상태 좋습니다.",
     *     "category": "TOY",
     *     "startAt": "2024-10-08T00:00:00",
     *     "endAt": "2024-10-10T00:00:00",
     *     "adminId": 1
     * }
     * res:
     * {
     *     "success": true,
     *     "result": {
     *         "title": "스탠리 텀블러",
     *         "content": "스탠리 상태 좋습니다.",
     *         "category": {},
     *         "startAt": "2024-10-08T00:00:00",
     *         "endAt": "2024-10-10T00:00:00",
     *         "adminId": null
     *     },
     *     "error": null
     * }
     */
    @PostMapping("")
    public ApiResponse<?> createProduct(@RequestBody ProductCreateRequestVO productRequestVO) {

        ProductDTO productRequestDTO = modelMapper.map(productRequestVO, ProductDTO.class);
        ProductDTO productResponseDTO = productService.createProduct(productRequestDTO);

        ProductCreateResponseVO productCreateResponseVO = modelMapper.map(productResponseDTO, ProductCreateResponseVO.class);

        return ApiResponse.ok(productCreateResponseVO);
    }
}
