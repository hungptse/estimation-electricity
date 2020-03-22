package hungpt.repositories;

import hungpt.entities.AccountEntity;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class AccountRepository extends BaseRepository<AccountEntity, String> {

    public AccountEntity checkLogin(String username, String password) {
        Query query = em.createNamedQuery("User.checkLogin");
        query.setParameter("username", username);
        query.setParameter("password", password);
//        query.setParameter("role", "admin");
        try {
            return (AccountEntity) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
