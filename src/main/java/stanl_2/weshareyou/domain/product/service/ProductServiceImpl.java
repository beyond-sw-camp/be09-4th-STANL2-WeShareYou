package stanl_2.weshareyou.domain.product.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
import stanl_2.weshareyou.domain.member.repository.MemberRepository;
import stanl_2.weshareyou.domain.product.aggregate.dto.ProductDTO;
import stanl_2.weshareyou.domain.product.aggregate.entity.Product;
import stanl_2.weshareyou.domain.product.aggregate.entity.ProuctCategory;
import stanl_2.weshareyou.domain.product.repository.ProductRepository;
import stanl_2.weshareyou.global.common.exception.CommonException;
import stanl_2.weshareyou.global.common.exception.ErrorCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    private static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMAT);

    @Autowired
    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository, MemberRepository memberRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {

        Member member = memberRepository.findById(productDTO.getAdminId())
                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND));

        Product product = new Product();
        product.setTitle(productDTO.getTitle());
        product.setContent(productDTO.getContent());
        product.setCategory(productDTO.getCategory());
        product.setStartAt(productDTO.getStartAt());
        product.setEndAt(productDTO.getEndAt());
        product.setImageUrl(productDTO.getImageUrl());
        product.setAdminId(member);
        product.setCreatedAt(LocalDateTime.now().format(FORMATTER));
        product.setUpdatedAt(LocalDateTime.now().format(FORMATTER));

        productRepository.save(product);

        ProductDTO productResponseDTO = modelMapper.map(product, ProductDTO.class);

        return productResponseDTO;
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(ProductDTO productDTO) {

        Member member = memberRepository.findById(productDTO.getAdminId())
                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND));

        Product productId = productRepository.findByIdAndAdminId(productDTO.getId(), member)
                .orElseThrow(() -> new CommonException(ErrorCode.PRODUCT_AUTHOR_NOT_FOUND));


        Product product = modelMapper.map(productDTO, Product.class);
        product.setUpdatedAt(LocalDateTime.now().format(FORMATTER));
        product.setAdminId(member);

        productRepository.save(product);

        ProductDTO productResponseDTO = modelMapper.map(product, ProductDTO.class);

        return productResponseDTO;
    }
}
