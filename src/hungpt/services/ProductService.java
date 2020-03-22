package hungpt.services;

import hungpt.entities.ProductEntity;
import hungpt.repositories.ProductRepository;

import java.util.Collections;
import java.util.List;

public class ProductService extends ProductRepository {

    public List<ProductEntity> findLikeByNameOrCode(String search){
        List<ProductEntity> result = findMany("Product.searchNameOrCode", Collections.singletonMap("search",search));
        return result;
    }

    public ProductEntity findOrCreate(ProductEntity entity){
        ProductEntity isExisted = getProductByHash(entity.getHash());
        if (isExisted == null){
            isExisted = create(entity);
        }
        return isExisted;
    }
}
