package stanl_2.weshareyou.domain.product.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.weshareyou.domain.product.aggregate.dto.ProductDTO;
import stanl_2.weshareyou.domain.product.aggregate.entity.Product;
import stanl_2.weshareyou.domain.product.aggregate.entity.ProuctCategory;
import stanl_2.weshareyou.domain.product.repository.ProductRepository;
import stanl_2.weshareyou.global.common.exception.CommonException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
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
    public ProductDTO createProduct(ProductDTO productRequestDTO) {

        Product product = modelMapper.map(productRequestDTO, Product.class);
        product.setCreatedAt(LocalDateTime.now().format(FORMATTER));
        product.setUpdatedAt(LocalDateTime.now().format(FORMATTER));

        productRepository.save(product);

        ProductDTO productResponseDTO = modelMapper.map(product, ProductDTO.class);

        return productResponseDTO;
    }
}
