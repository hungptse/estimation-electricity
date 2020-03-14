package hungpt.ws;

import com.sun.net.httpserver.HttpServer;
import hungpt.constant.EntityName;
import hungpt.entities.PriceListEntity;
import hungpt.entities.ProductEntity;
import hungpt.jaxb.dienmayabc.product.Product;
import hungpt.repositories.MainRepository;
import hungpt.services.ProductService;
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

    private ProductService service = new ProductService();

    @GET()
    @Produces({MediaType.APPLICATION_XML})
    public List<ProductEntity> findAll(@QueryParam("page") int page, @QueryParam("size") int size) {
        return service.getAllProductPaging(page,size);
    }

    @GET()
    @Path("/findLikeByNameOrCode")
    @Produces({MediaType.APPLICATION_XML})
    public List<ProductEntity> findLikeByName(@QueryParam("search") String search) {
        return service.findLikeByNameOrCode("%" + search + "%");
    }


}
