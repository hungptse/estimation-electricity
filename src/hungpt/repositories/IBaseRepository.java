package hungpt.repositories;

import com.sun.istack.Nullable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IBaseRepository<T, PK extends Serializable> {
    T findById(PK primaryKey);

    T find(String query, Map<String, Object> parameters);

    List<T> findMany(String query, Map<String, Object> parameters);

    T create(T entity);

    T update(T entity);
}
