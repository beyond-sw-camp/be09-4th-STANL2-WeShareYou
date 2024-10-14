package stanl_2.weshareyou.domain.product.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
import stanl_2.weshareyou.domain.product.aggregate.entity.Product;
import stanl_2.weshareyou.domain.product.aggregate.entity.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndAdminId(Long id, Member adminId);

    Slice<Product> findByCategoryOrderByCreatedAtDesc(ProductCategory category, Pageable pageable);

    Slice<Product> findByCategoryLessThanOrderByCreatedAtDesc(ProductCategory category, Pageable pageable);
}
