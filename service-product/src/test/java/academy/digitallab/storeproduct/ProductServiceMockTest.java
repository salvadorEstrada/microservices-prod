package academy.digitallab.storeproduct;

import academy.digitallab.storeproduct.entity.Category;
import academy.digitallab.storeproduct.entity.Product;
import academy.digitallab.storeproduct.repository.ProductRepository;
import academy.digitallab.storeproduct.service.ProductServiceImpl;
import academy.digitallab.storeproduct.service.ProductoService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ProductServiceMockTest {
    @Mock
    private ProductRepository productRepository;
    //En la clase ProductServiceImpl se hizo la inyección por constructor
    private ProductoService productoService;

    @BeforeEach //Se debe de ejecutar antes de realizar nuestro test
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        productoService = new ProductServiceImpl(productRepository);

        Product computer = Product.builder()
                .id(1L)
                .name("computer")
                .category(Category.builder().id(1L).build())
                .price(Double.parseDouble("12.5"))
                .stock(Double.parseDouble("5"))
                .build();

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(computer));
        //Actualizar a la nueva computer
        Mockito.when(productRepository.save(computer)).thenReturn(computer);
    }

   @Test
   public void whenValidGetID_ThenReturnProduct(){
        Product found = productoService.getProduct(1L);
       Assertions.assertThat(found.getName()).isEqualTo("computer");
   }


   //Verificar si nuestra lógica de negocio esta trabajando bien
   @Test
    public void whenValidUpdateStock_thenReturnNewStock(){
        Product newStock = productoService.updateStock(1L, Double.parseDouble("8"));
        Assertions.assertThat(newStock.getStock()).isEqualTo(13);
    }


}
