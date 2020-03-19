package hungpt.utils;

import hungpt.constant.GlobalURL;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JPAHelper {
    private static EntityManagerFactory managerFactory;

    public static EntityManager getEntityManager(){
        try {
            if (managerFactory == null){
                managerFactory = Persistence.createEntityManagerFactory(GlobalURL.PERSISTENT_UNIT);
            }
            return managerFactory.createEntityManager();
        } catch (Exception e){
            Logger.getLogger(JPAHelper.class.getName()).log(Level.SEVERE,null,e);
            e.printStackTrace();
        }
        return null;
    }
}
