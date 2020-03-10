package hungpt.repositories;

import hungpt.constant.EntityName;
import hungpt.entities.ProductEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainRepository {

    private static Map<EntityName, BaseRepository> entites = new HashMap<>();

    static {
        entites.put(EntityName.PRODUCT_ENTITY, new BaseRepository<ProductEntity,String>(ProductEntity.class));
    }

    public static BaseRepository getEntityByName(EntityName name){
        return entites.get(name);
    }
}
