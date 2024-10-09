package stanl_2.weshareyou.domain.product.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
import stanl_2.weshareyou.domain.product.aggregate.dto.ProductDTO;
import stanl_2.weshareyou.domain.product.aggregate.entity.Product;
import stanl_2.weshareyou.domain.product.aggregate.entity.ProuctCategory;
import stanl_2.weshareyou.domain.product.repository.ProductRepository;
import stanl_2.weshareyou.global.common.exception.CommonException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    private static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMAT);

    @Autowired
    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO productCreateRequestDTO) {
        log.info("id1:{}", productCreateRequestDTO.getAdminId());
//        Product product = modelMapper.map(productCreateRequestDTO, Product.class);
        Product product = new Product();
        product.setTitle(productCreateRequestDTO.getTitle());
        product.setContent(productCreateRequestDTO.getContent());
        product.setCategory(productCreateRequestDTO.getCategory());
        product.setStartAt(productCreateRequestDTO.getStartAt());
        product.setEndAt(productCreateRequestDTO.getEndAt());
        product.setImageUrl(productCreateRequestDTO.getImageUrl());

        // Member pull 받으면 수정예정
        Member adminId = new Member();
        adminId.setId(productCreateRequestDTO.getId());
        product.setAdminId(adminId);

        product.setCreatedAt(LocalDateTime.now().format(FORMATTER));
        product.setUpdatedAt(LocalDateTime.now().format(FORMATTER));
        log.info("id2:{}", product.getAdminId());
        productRepository.save(product);

        ProductDTO productResponseDTO = modelMapper.map(product, ProductDTO.class);

        return productResponseDTO;
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(ProductDTO productUpdateRequestDTO) {

        Member adminId = new Member();
        adminId.setId(productUpdateRequestDTO.getId());

        Product product = modelMapper.map(productUpdateRequestDTO, Product.class);
        product.setUpdatedAt(LocalDateTime.now().format(FORMATTER));
        product.setAdminId(adminId);

        productRepository.save(product);

        ProductDTO productResponseDTO = modelMapper.map(product, ProductDTO.class);

        return productResponseDTO;
    }
}
