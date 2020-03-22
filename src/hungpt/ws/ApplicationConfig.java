package hungpt.ws;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

@ApplicationPath("webservice")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {

        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(ProductWS.class);
        resources.add(EstimateWS.class);
        resources.add(AccountWS.class);
        return resources;
    }
}
