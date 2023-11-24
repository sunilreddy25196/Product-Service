package com.example.demo.Services;

import com.example.demo.DTOS.FakeStoreProductDto;
import com.example.demo.DTOS.GenericProductDTO;
import com.example.demo.Exceptions.ProductNotFoundException;
import com.example.demo.Models.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("FakeStoreProdService")

public class FakeStoreProdService implements ProductService
{
    private RestTemplateBuilder restTemplateBuilder;
    private String specificProductUrl = "https://fakestoreapi.com/products/{id}";
    private String genericProductUrl = "https://fakestoreapi.com/products/";
    FakeStoreProdService(RestTemplateBuilder restTemplateBuilder1)
    {
        this.restTemplateBuilder=restTemplateBuilder1;
    }
     public GenericProductDTO ConvertToGenericProductDTO(FakeStoreProductDto fakeStoreProductDto)
     {
         GenericProductDTO genericProductDTO=new GenericProductDTO();
         genericProductDTO.setId(fakeStoreProductDto.getId());
         genericProductDTO.setCategory(fakeStoreProductDto.getCategory());
         genericProductDTO.setTitle(fakeStoreProductDto.getTitle());
         genericProductDTO.setPrice(fakeStoreProductDto.getPrice());
         genericProductDTO.setImage(fakeStoreProductDto.getImage());
         genericProductDTO.setDescription(fakeStoreProductDto.getDescription());
         return genericProductDTO;
     }
    @Override
    public GenericProductDTO GetProductById(Long id) throws  ProductNotFoundException
    {
        RestTemplate restTemplate=restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> fakestoreResult=restTemplate.getForEntity(specificProductUrl,FakeStoreProductDto.class,id);
        if(fakestoreResult==null)
            throw new ProductNotFoundException("Product with id :"+id+" not exists");
        return ConvertToGenericProductDTO(fakestoreResult.getBody());

    }

    @Override
    public List<GenericProductDTO> GetAllProducts() {
        RestTemplate restTemplate=restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> fakeDTOList=restTemplate.getForEntity(genericProductUrl,FakeStoreProductDto[].class);
        List<GenericProductDTO> listGenericDTO=new ArrayList();
        List<FakeStoreProductDto> listFakestoreDTO = List.of(fakeDTOList.getBody());
        for(FakeStoreProductDto fakeDTO:listFakestoreDTO)
        {
            listGenericDTO.add(ConvertToGenericProductDTO(fakeDTO));
        }
        return listGenericDTO;
    }

    @Override
    public GenericProductDTO deleteProductByID(Long id) {
        RestTemplate restTemplate=restTemplateBuilder.build();
        RequestCallback  requestCallback=restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor=restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity=restTemplate.execute(specificProductUrl, HttpMethod.DELETE,requestCallback,responseExtractor,id);
        return ConvertToGenericProductDTO(fakeStoreProductDtoResponseEntity.getBody());

    }

    @Override
    public GenericProductDTO createProduct(GenericProductDTO productDTO) {
        return null;
    }

    @Override
    public GenericProductDTO updateProductByID(Long id) {
       RestTemplate restTemplate=restTemplateBuilder.build();
       RequestCallback requestCallBack=restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
       ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor=
               restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
       ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity=
               restTemplate.execute(genericProductUrl,HttpMethod.PUT,requestCallBack,responseExtractor,id);
       return ConvertToGenericProductDTO(fakeStoreProductDtoResponseEntity.getBody());

    }
}
