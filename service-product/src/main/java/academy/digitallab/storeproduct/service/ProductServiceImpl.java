package academy.digitallab.storeproduct.service;

import academy.digitallab.storeproduct.entity.Category;
import academy.digitallab.storeproduct.entity.Product;
import academy.digitallab.storeproduct.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor //Inyección por constructor
public class ProductServiceImpl implements ProductoService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> listaAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        product.setStatus("CREATED");
        product.setCreateAt(new Date());

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product productDB = getProduct(product.getId());//Verificar si existe el prod
        if(null==productDB){
            return null;
        }
        productDB.setName(product.getName());
        productDB.setDescription(product.getDescription());
        productDB.setCategory(product.getCategory());
        productDB.setPrice(product.getPrice());

        return productRepository.save(productDB);
    }

    @Override
    public Product deleteProduct(Long id) {
        Product productDB = getProduct(id);
        if(null==productDB){
            return null;
        }
        productDB.setStatus("DELETED");
        return productRepository.save(productDB);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Double quantity) {
        //Product p = productRepository.findById(id).orElseThrow(null);
        Product productDB = getProduct(id);
        if(null==productDB){
            return null;
        }
        Double stock = productDB.getStock()+quantity;
        productDB.setStock(stock);
        return productRepository.save(productDB);
    }


}
