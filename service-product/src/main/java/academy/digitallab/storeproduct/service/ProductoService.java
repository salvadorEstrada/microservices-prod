package academy.digitallab.storeproduct.service;

import academy.digitallab.storeproduct.entity.Category;
import academy.digitallab.storeproduct.entity.Product;

import java.util.List;

public interface ProductoService {
    public List<Product> listaAllProduct();
    public Product getProduct(Long id);

    public Product createProduct(Product product);
    public Product updateProduct(Product product);
    public Product deleteProduct(Long id);
    public List<Product> findByCategory(Category category);
    public Product updateStock(Long id, Double quantity);

}
