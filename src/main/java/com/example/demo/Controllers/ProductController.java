package com.example.demo.Controllers;

import com.example.demo.DTOS.GenericProductDTO;
import com.example.demo.Exceptions.ProductNotFoundException;
import com.example.demo.Services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    ProductService productService;
    ProductController(@Qualifier("FakeStoreProdService") ProductService prodService)
    {
        this.productService=prodService;
    }
    @GetMapping("/{id}")
    public GenericProductDTO  GetProductById(@PathVariable Long id) throws ProductNotFoundException
    {
        return new GenericProductDTO();
        //return productService.GetProductById(id);
    }
    @GetMapping
    public List<GenericProductDTO> GetAllProducts()
    {
        return productService.GetAllProducts();
    }
    @DeleteMapping("/{id}")
    public GenericProductDTO DeleteProductById(@PathVariable Long id)
    {
        return productService.deleteProductByID(id);
    }

}
