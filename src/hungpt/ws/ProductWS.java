package hungpt.ws;

import hungpt.entities.ProductEntity;
import hungpt.services.ProductService;

import java.util.List;

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
        return service.findProductPaging(page,size);
    }

    @GET()
    @Path("/findLikeByNameOrCode")
    @Produces({MediaType.APPLICATION_XML})
    public List<ProductEntity> findLikeByName(@QueryParam("search") String search) {
        return service.findLikeByNameOrCode("%" + search + "%");
    }


}
