package hungpt.services;

import hungpt.entities.CategoryEntity;
import hungpt.repositories.CategoryRepository;
import hungpt.utils.HashHepler;

public class CategoryService extends CategoryRepository {

    public CategoryEntity findOrCreateCustomizeCategory(String name){
        CategoryEntity isExisted = getCategoryByHash(HashHepler.hashMD5(name));
        if (isExisted == null){
            isExisted = create(new CategoryEntity(name));
        }
        return isExisted;
    }
}
