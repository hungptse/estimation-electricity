package hungpt.ws;
import com.sun.net.httpserver.HttpServer;
import hungpt.constant.EntityName;
import hungpt.entities.PriceListEntity;
import hungpt.entities.ProductEntity;
import hungpt.jaxb.dienmayabc.product.Product;
import hungpt.repositories.MainRepository;
import hungpt.utils.ElectricityHelper;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/product")
public class ProductWS {

    @GET()
    @Produces({MediaType.APPLICATION_XML})
    public List<ProductEntity> findAll() {
        List<ProductEntity> all = (List<ProductEntity>) MainRepository.getEntityByName(EntityName.PRODUCT_ENTITY).findMany("SELECT * FROM Product",null);
        return all;
    }

    @GET()
    @Path("/test")
    public List<ProductEntity> test() {
        List<PriceListEntity> all = (List<PriceListEntity>) MainRepository.getEntityByName(EntityName.PRICE_LIST_ENTITY).findMany(null,null);

        System.out.println(ElectricityHelper.calculateByLevel(all,1000));;

        return null;
    }
}
