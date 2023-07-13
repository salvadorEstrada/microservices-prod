package academy.digitallab.storeproduct;

import academy.digitallab.storeproduct.entity.Category;
import academy.digitallab.storeproduct.entity.Product;
import academy.digitallab.storeproduct.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.assertj.core.api.Assertions;


import java.util.Date;
import java.util.List;

@DataJpaTest
public class ProductRepositoryMockTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenFindByCategory_thenReturnListProduct(){
        //builder() instead of new
        Product product01= Product.builder()
                .name("Computer")
                .category(Category.builder().id(1L).build())//Se inserta un nuevo producto con la category 1, por lo que se espera que haya 3
                .description("")
                .stock(Double.parseDouble("10"))
                .price(Double.parseDouble("1240.99"))
                .status("Created")
                .createAt(new Date())
                .build();
        productRepository.save(product01);


        List<Product> found = productRepository.findByCategory(product01.getCategory());
        Assertions.assertThat(found.size()).isEqualTo(3);
    }

}
