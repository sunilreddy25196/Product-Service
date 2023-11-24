package com.example.demo.Services;

import com.example.demo.DTOS.GenericProductDTO;
import com.example.demo.Exceptions.ProductNotFoundException;

import java.lang.reflect.GenericArrayType;
import  java.util.List;
public interface ProductService {
    GenericProductDTO GetProductById(Long id) throws ProductNotFoundException;
    List<GenericProductDTO> GetAllProducts();
    GenericProductDTO deleteProductByID(Long id);
    GenericProductDTO createProduct(GenericProductDTO productDTO);
    GenericProductDTO updateProductByID(Long id);

}

