package academy.digitallab.storeproduct.controller;

import academy.digitallab.storeproduct.entity.Category;
import academy.digitallab.storeproduct.entity.Product;
import academy.digitallab.storeproduct.service.ProductoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductoService productoService;
    //Esta lógica trae una categoria y sus productos asociados
    @GetMapping("/{categoryId}")
    public ResponseEntity<?> listProduct(@PathVariable(name="categoryId") Long categoryId){
        List<Product> products = new ArrayList<>();
        if(null==categoryId){
            products= productoService.listaAllProduct();
            if(products.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else{
            products = productoService.findByCategory(Category.builder().id(categoryId).build());
            if(products.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.ok(products);
    }

    @GetMapping("/prod/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long productId){
      Product product=productoService.getProduct(productId);
      if(productId==null){
          return ResponseEntity.noContent().build();
      }
      return  ResponseEntity.ok(product);
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Product prod = productoService.createProduct(product);
       return ResponseEntity.status(HttpStatus.CREATED).body(prod);
    }

    @PutMapping("update/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product product){
        product.setId(productId);
        Product productDb = productoService.updateProduct(product);
        if(null==productDb){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDb);
    }

    @DeleteMapping("delete/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long productId){
        Product prodDb = productoService.deleteProduct(productId);
        if(null==prodDb){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(prodDb);
    }

    @GetMapping("/{productId}/stock")
    public ResponseEntity<Product> updateStockProduct(@PathVariable Long productId, @RequestParam(name="quantity", required = false)  Double quantity){
        Product product =productoService.updateStock(productId,quantity);
        if(null==product){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    private String formatMessage(BindingResult result){
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                        Map<String, String> error = new HashMap<>();
                        error.put(err.getField(), err.getDefaultMessage());
                        return  error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .message(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString ="";
        try{
            jsonString = mapper.writeValueAsString(errorMessage);
        }catch(JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }


}
