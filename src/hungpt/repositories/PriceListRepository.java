package hungpt.repositories;

import hungpt.entities.PriceListEntity;
import hungpt.entities.ProductEntity;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class PriceListRepository extends BaseRepository<PriceListEntity, Integer> {

    public PriceListRepository(Class<PriceListEntity> classType) {
        super(classType);
    }

    public PriceListRepository() {
        super(PriceListEntity.class);
    }

    public PriceListEntity getPriceListByHash(String hash) {
        Query query = em.createNamedQuery("PriceList.findByHash");
        query.setParameter("hash", hash);
        try {
            return (PriceListEntity) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
