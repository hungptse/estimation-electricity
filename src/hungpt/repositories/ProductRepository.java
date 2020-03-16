package hungpt.repositories;

import hungpt.entities.ProductEntity;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class ProductRepository extends BaseRepository<ProductEntity, Integer> {
    public ProductRepository(Class<ProductEntity> classType) {
        super(classType);
    }

   public ProductRepository(){
        super(ProductEntity.class);
   }

   public ProductEntity getProductByHash(String hash){
       Query query = em.createNamedQuery("Product.findByHash");
       query.setParameter("hash",hash);
       try {
           return (ProductEntity) query.getSingleResult();
       } catch (NoResultException e){
           return null;
       }
   }
}
