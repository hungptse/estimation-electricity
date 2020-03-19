package hungpt.ws;

import hungpt.dto.estimate.req.Products;
import org.w3c.dom.Document;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/estimate")
public class EstimateWS {

    @POST()
    @Consumes({MediaType.APPLICATION_XML})
    public void calculate(Products request) {
        System.out.println(request.getProduct().size());
        System.out.println("test WS");
    }
}
