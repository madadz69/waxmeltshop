package com.dieselscosyscents.waxmeltshop;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.dieselscosyscents.waxmeltshop.product.Product;
import com.dieselscosyscents.waxmeltshop.product.ProductController;
import com.dieselscosyscents.waxmeltshop.product.ProductRepository;
import java.util.List;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    
    @Autowired
    MockMvc mockMvc;
    
    @MockitoBean
    ProductRepository productRepository;

    @Test
    public void addProductShouldReturnCreatedResponseWithProduct() throws Exception {
        Product product = new Product(1L, "Test", "Test Description", 10.0, null);
        
        when(productRepository.save(any(Product.class))).thenReturn(product);
        
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test\",\"description\":\"Test Description\",\"price\":10.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.price").value(10.0));
    }

    @Test
    public void searchProductsShouldReturnStatusOK() throws Exception {
        List<Product> mockProducts = List.of(
            new Product(1L, "Test", "Test Description", 10.0, null),
            new Product(2L, "Test2", "Test Description 2", 15.0, null)
        );

        when(productRepository.searchProductsByName("test")).thenReturn(mockProducts);

        mockMvc.perform(get("/products/search?q=test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Test"))
                .andExpect(jsonPath("$[1].name").value("Test2"));
    }
}
