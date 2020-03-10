package hungpt.utils;

import hungpt.constant.GlobalURL;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAHelper {
    private static EntityManagerFactory managerFactory;

    public static EntityManager getEntityManager(){
        try {
            if (managerFactory == null){
                managerFactory = Persistence.createEntityManagerFactory(GlobalURL.PERSISTENT_UNIT);
            }
            return managerFactory.createEntityManager();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
