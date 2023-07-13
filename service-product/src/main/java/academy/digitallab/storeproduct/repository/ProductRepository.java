package academy.digitallab.storeproduct.repository;

import academy.digitallab.storeproduct.entity.Category;
import academy.digitallab.storeproduct.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
}
