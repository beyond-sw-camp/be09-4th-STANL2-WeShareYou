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
import stanl_2.weshareyou.domain.product.aggregate.entity.ProductCategory;
import stanl_2.weshareyou.domain.product.repository.ProductRepository;
import stanl_2.weshareyou.global.common.exception.CommonException;
import stanl_2.weshareyou.global.common.exception.ErrorCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    @Transactional
    public ProductDTO deleteProduct(ProductDTO productDTO) {

        Member member = memberRepository.findById(productDTO.getAdminId())
                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND));

        Product productId = productRepository.findByIdAndAdminId(productDTO.getId(), member)
                .orElseThrow(() -> new CommonException(ErrorCode.PRODUCT_AUTHOR_NOT_FOUND));

        Product product = new Product();
        product.setId(productDTO.getId());
        product.setAdminId(member);

        productRepository.delete(product);

        ProductDTO productResponseDTO = modelMapper.map(product, ProductDTO.class);

        return productResponseDTO;
    }

    @Override
    @Transactional
    public List<ProductDTO> readAllProductList() {

        List<Product> productList = productRepository.findAll();

        if (productList.isEmpty()) {
            throw new CommonException(ErrorCode.PRODUCT_NOT_FOUND);
        } else {
            List<ProductDTO> productDTOList = productList.stream()
                    .map(productdto -> modelMapper.map(productdto, ProductDTO.class))
                    .collect(Collectors.toList());

            return productDTOList;
        }
    }

    @Override
    @Transactional
    public ProductDTO readProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CommonException(ErrorCode.PRODUCT_NOT_FOUND));

        ProductDTO productResponseDTO = modelMapper.map(product, ProductDTO.class);

        return productResponseDTO;
    }

    @Override
    @Transactional
    public List<ProductDTO> readProductByCategory(String category) {
        ProductCategory enumCategory = ProductCategory.valueOf(category.toUpperCase());
        List<Product> productList = productRepository.findByCategory(enumCategory);

        if (productList.isEmpty()) {
            throw new CommonException(ErrorCode.PRODUCT_NOT_FOUND);
        } else {
            List<ProductDTO> productDTOList = new ArrayList<>();
            for (Product product : productList) {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(product.getId());
                productDTO.setTitle(product.getTitle());
                productDTO.setImageUrl(product.getImageUrl());
                productDTO.setCategory(product.getCategory());
                productDTO.setRental(product.isRental());

                productDTOList.add(productDTO);
            }
            return productDTOList;
        }
    }

    @Override
    @Transactional
    public ProductDTO updateRentalProduct(Long proudctId, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND));

        Product product = productRepository.findById(proudctId)
                .orElseThrow(() -> new CommonException(ErrorCode.PRODUCT_NOT_FOUND));

        if (product.isRental()) {
            throw new CommonException(ErrorCode.PRODUCT_IS_RENTAL);
        } else {
            product.setMemberId(member);

            productRepository.save(product);

            ProductDTO productResponseDTO = new ProductDTO();
            productResponseDTO.setId(product.getId());
            productResponseDTO.setRental(product.isRental());
            productResponseDTO.setMemberId(product.getMemberId().getId());

            return productResponseDTO;
        }
    }

    @Override
    @Transactional
    public ProductDTO updateRentalApproveProduct(Long productId, Long adminId) {

        Member member = memberRepository.findById(adminId)
                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CommonException(ErrorCode.PRODUCT_NOT_FOUND));

        if (!product.isRental() && product.getMemberId() != null) {
            product.setRental(true);

            productRepository.save(product);

            ProductDTO productResponseDTO = new ProductDTO();
            productResponseDTO.setId(product.getId());
            productResponseDTO.setRental(product.isRental());
            productResponseDTO.setMemberId(product.getMemberId().getId());

            return productResponseDTO;
        } else {
            throw new CommonException(ErrorCode.PRODUCT_IS_RENTAL);
        }
    }

}
