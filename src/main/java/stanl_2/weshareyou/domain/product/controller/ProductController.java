package stanl_2.weshareyou.domain.product.controller;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stanl_2.weshareyou.domain.product.aggregate.dto.ProductDTO;
import stanl_2.weshareyou.domain.product.aggregate.vo.request.ProductCreateRequestVO;
import stanl_2.weshareyou.domain.product.aggregate.vo.request.ProductUpdateRequestVO;
import stanl_2.weshareyou.domain.product.aggregate.vo.response.ProductCreateResponseVO;
import stanl_2.weshareyou.domain.product.aggregate.vo.response.ProductUpdateResponseVO;
import stanl_2.weshareyou.domain.product.service.ProductService;
import stanl_2.weshareyou.global.common.response.ApiResponse;

@RestController
@RequestMapping("/api/v1/product")
@Slf4j
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
     *         "title": "스탠리 텀블러2",
     *         "content": "스탠리 상태 좋습니다2.",
     *         "imageUrl": null,
     *         "category": "TOY",
     *         "startAt": "2024-10-08T00:00:00",
     *         "endAt": "2024-10-10T00:00:00",
     *         "adminId": 1
     *     },
     *     "error": null
     * }
     */
    @PostMapping("")
    public ApiResponse<?> createProduct(@RequestBody ProductCreateRequestVO productCreateRequestVO) {

        ProductDTO productRequestDTO = new ProductDTO();
        productRequestDTO.setAdminId(productCreateRequestVO.getAdminId());
        productRequestDTO.setTitle(productCreateRequestVO.getTitle());
        productRequestDTO.setContent(productCreateRequestVO.getContent());
        productRequestDTO.setCategory(productCreateRequestVO.getCategory());
        productRequestDTO.setStartAt(productCreateRequestVO.getStartAt());
        productRequestDTO.setEndAt(productCreateRequestVO.getEndAt());
        productRequestDTO.setImageUrl(productCreateRequestVO.getImageUrl());

        ProductDTO productResponseDTO = productService.createProduct(productRequestDTO);

        ProductCreateResponseVO productCreateResponseVO = modelMapper.map(productResponseDTO, ProductCreateResponseVO.class);

        return ApiResponse.ok(productCreateResponseVO);
    }

    /**
     * 내용: 공유물품 수정
     * req:
     * {
     *     "id": 4,
     *     "title": "스탠리 텀블러1",
     *     "content": "스탠리 상태 좋습니다1.",
     *     "category": "KITCHENWARES",
     *     "startAt": "2024-10-08T00:00:00",
     *     "endAt": "2024-10-10T00:00:00",
     *     "adminId": 1
     * }
     * res:
     * {
     *     "success": true,
     *     "result": {
     *         "id": 4,
     *         "title": "스탠리 텀블러1",
     *         "content": "스탠리 상태 좋습니다1.",
     *         "category": "KITCHENWARES",
     *         "startAt": "2024-10-08T00:00:00",
     *         "endAt": "2024-10-10T00:00:00",
     *         "adminId": 1
     *     },
     *     "error": null
     * }
     */
    @PutMapping("")
    public ApiResponse<?> updateProduct(@RequestBody ProductUpdateRequestVO productUpdateRequestVO) {

        ProductDTO productRequestDTO = modelMapper.map(productUpdateRequestVO, ProductDTO.class);
        ProductDTO productResponseDTO = productService.updateProduct(productRequestDTO);

        ProductUpdateResponseVO productUpdateResponseVO = modelMapper.map(productResponseDTO, ProductUpdateResponseVO.class);

        return ApiResponse.ok(productUpdateResponseVO);
    }
}
