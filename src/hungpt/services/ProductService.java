package hungpt.services;

import hungpt.constant.EntityName;
import hungpt.entities.ProductEntity;
import hungpt.repositories.MainRepository;
import hungpt.repositories.ProductRepository;

import java.util.Collections;
import java.util.List;

public class ProductService {

    ProductRepository repository = new ProductRepository();

    public List getAllProductPaging(int page, int size){
        List result = repository.findProductPaging(page,size);
        return result;
    }

    public List<ProductEntity> findLikeByNameOrCode(String search){
        List<ProductEntity> result = (List<ProductEntity>) MainRepository.getEntityByName(EntityName.PRODUCT_ENTITY).findMany("Product.searchNameOrCode", Collections.singletonMap("search",search));
        return result;
    }
    public List<ProductEntity> findProductByListId(List<Integer> ids){
        List<ProductEntity> result = repository.findProductByListId(ids);
        return result;
    }
}
