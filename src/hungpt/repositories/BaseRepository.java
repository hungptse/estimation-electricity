package hungpt.repositories;

import hungpt.utils.JPAHelper;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class BaseRepository<T,PK extends Serializable> implements IBaseRepository<T, PK> {

    protected EntityManager em;
    protected final Class<T> classType;

    public BaseRepository(Class<T> classType) {
        this.em = JPAHelper.getEntityManager();
        this.classType = classType;
    }

    @Override
    public T findById(PK primaryKey) {
        try {
            return em.find(classType,primaryKey);
        } finally {
            if (em != null){
                em.close();
            }
        }
    }

    @Override
    public T find(String query, Map<String, Object> parameters) {
        return null;
    }

    @Override
    public List<T> findMany(String query, Map<String, Object> parameters) {
        return null;
    }

    @Override
    public T create(T entity) {
        if (entity == null) {
            return null;
        }
        em = JPAHelper.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            entity = em.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("ERROR in BaseRepository " + e.getMessage());
        } finally {
            if (em != null){
                em.close();
            }
        }
        return entity;
    }

    @Override
    public T update(T entity) {
        return null;
    }
}
