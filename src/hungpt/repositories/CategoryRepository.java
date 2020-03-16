package hungpt.repositories;

import hungpt.entities.CategoryEntity;
import hungpt.jaxb.dienmayabc.category.Category;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class CategoryRepository extends BaseRepository<CategoryEntity, Integer> {
    public CategoryRepository(Class<CategoryEntity> classType) {
        super(classType);
    }

    public CategoryRepository() {
        super(CategoryEntity.class);
    }

    public CategoryEntity getCategoryByHash(String hash){
        Query query = em.createNamedQuery("Category.findByHash");
        query.setParameter("hash",hash);
        try{
            return (CategoryEntity) query.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

}
