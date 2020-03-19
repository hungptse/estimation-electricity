package hungpt.services;

import hungpt.constant.EntityName;
import hungpt.entities.ProductEntity;
import hungpt.repositories.MainRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {

    public List<ProductEntity> getAllProductPaging(int page, int size){
        List<ProductEntity> result = (List<ProductEntity>) MainRepository.getEntityByName(EntityName.PRODUCT_ENTITY).findManyPaging("Product.findPageAndSize", page, size);
        return result.stream().map(item -> new ProductEntity(item.getProductId(),item.getName(),item.getCode(),item.getUrl(),item.getImageLink(),item.getWattage())).collect(Collectors.toList());
    }

    public List<ProductEntity> findLikeByNameOrCode(String search){
        List<ProductEntity> result = (List<ProductEntity>) MainRepository.getEntityByName(EntityName.PRODUCT_ENTITY).findMany("Product.searchNameOrCode", Collections.singletonMap("search",search));
        return result;
    }
}
