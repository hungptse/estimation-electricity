package hungpt.ws;

import com.sun.net.httpserver.HttpServer;
import hungpt.constant.EntityName;
import hungpt.entities.PriceListEntity;
import hungpt.entities.ProductEntity;
import hungpt.jaxb.dienmayabc.product.Product;
import hungpt.repositories.MainRepository;
import hungpt.utils.ElectricityHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/product")
public class ProductWS {

    @GET()
    @Produces({MediaType.APPLICATION_XML})
    public List<ProductEntity> findAll(@QueryParam("page") int page, @QueryParam("size") int size) {
        List<ProductEntity> all = (List<ProductEntity>) MainRepository.getEntityByName(EntityName.PRODUCT_ENTITY).findMany("Product.findPageAndSize", page, size);
        return all;
    }

    @GET()
    @Path("/test")
    public List<ProductEntity> test() {
//        System.out.println(ElectricityHelper.calculateByLevel(all, 1000));

        return null;
    }
}
