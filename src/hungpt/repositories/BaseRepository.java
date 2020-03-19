package hungpt.repositories;

import com.sun.istack.Nullable;
import hungpt.utils.JPAHelper;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseRepository<T, PK extends Serializable> implements IBaseRepository<T, PK> {

    protected EntityManager em;
    protected final Class<T> classType;

    public BaseRepository(Class<T> classType) {
        this.em = JPAHelper.getEntityManager();
        this.classType = classType;
    }

    @Override
    public T findById(PK primaryKey) {
        return em.find(classType, primaryKey);
    }

    @Override
    public T find(String query, Map<String, Object> parameters) {

        if (query == null) {
            return null;
        }
        T result = null;
        try {
            Query sql =  em.createNamedQuery(query);
            if (parameters != null && !parameters.isEmpty()) {
                for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                    sql.setParameter(entry.getKey(), entry.getValue());
                }
            }
            if (sql.getMaxResults() != 0){
                 result = (T) sql.getResultList();
            }
        } catch (Exception e) {
            Logger.getLogger(BaseRepository.class.getName()).log(Level.SEVERE,null,e);
        }
        return result;
    }

    @Override
    public List<T> findManyPaging(String query, int page, int size) {
        try {
            return em.createNamedQuery(query).setMaxResults(size).setFirstResult((page - 1) * size).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<T> findMany(String query, Map<String, Object> parameters) {
        try {
            Query sql =  em.createNamedQuery(query);
            if (parameters != null && !parameters.isEmpty()) {
                for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                    sql.setParameter(entry.getKey(), entry.getValue());
                }
            }
            if (sql.getMaxResults() != 0){
                return sql.getResultList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        }
        return entity;
    }

    @Override
    public T update(T entity) {
        return null;
    }

}
