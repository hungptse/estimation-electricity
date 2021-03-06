package hungpt.repositories;

import hungpt.entities.ProductEntity;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;


public class ProductRepository extends BaseRepository<ProductEntity, Integer> {
   public ProductEntity getProductByHash(String hash){
       Query query = em.createNamedQuery("Product.findByHash");
       query.setParameter("hash",hash);
       try {
           return (ProductEntity) query.getSingleResult();
       } catch (NoResultException e){
           return null;
       }
   }

   public List<ProductEntity> findProductByListId(List<Integer> ids){
        Query query = em.createNamedQuery("Product.findByIds");
        query.setParameter("lisdId",ids);
        return query.getResultList();
   }

    public List findProductPaging(int page, int size){
        Query query = em.createNamedQuery("Product.findPageAndSize");
        query.setFirstResult((page - 1) * size).setMaxResults(size);
        return query.getResultList();
    }

}
