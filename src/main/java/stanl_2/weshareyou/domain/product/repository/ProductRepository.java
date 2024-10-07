package stanl_2.weshareyou.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.weshareyou.domain.product.aggregate.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
