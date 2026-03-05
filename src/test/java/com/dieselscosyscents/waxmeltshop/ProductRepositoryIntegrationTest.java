package com.dieselscosyscents.waxmeltshop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.dieselscosyscents.waxmeltshop.product.Product;
import com.dieselscosyscents.waxmeltshop.product.ProductRepository;

@SpringBootTest
@Transactional
public class ProductRepositoryIntegrationTest {
    
    @Autowired
    ProductRepository productRepository;
    
    @BeforeEach
    public void setup() {
        productRepository.deleteAll();
    }
    
    @Test
    public void searchProductsByNameShouldFindMatchingProducts() {
        productRepository.save(new Product(null, "Vanilla Melt", "Sweet scent", 5.99, null));
        productRepository.save(new Product(null, "Chocolate Vanilla", "Rich scent", 6.99, null));
        productRepository.save(new Product(null, "Lavender", "Calming scent", 4.99, null));
        
        List<Product> results = productRepository.searchProductsByName("vanilla");
        
        assertEquals(2, results.size());
    }
    
    @Test
    public void searchProductsByNameShouldBeCaseInsensitive() {
        productRepository.save(new Product(null, "Vanilla Melt", "Sweet scent", 5.99, null));
        
        List<Product> results = productRepository.searchProductsByName("VANILLA");
        
        assertEquals(1, results.size());
        assertEquals("Vanilla Melt", results.get(0).getName());
    }
}
