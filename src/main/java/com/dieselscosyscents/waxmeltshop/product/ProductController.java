package com.dieselscosyscents.waxmeltshop.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product savedProduct = productRepository.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam("q") String q) {
        return productRepository.searchProductsByName(q);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id) {
        productRepository.deleteById(id);
    }
}
