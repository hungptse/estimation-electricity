package hungpt.ws;

import hungpt.dto.estimate.req.Product;
import hungpt.dto.estimate.req.Products;
import hungpt.entities.ProductEntity;
import hungpt.services.PriceListService;
import hungpt.services.ProductService;
import hungpt.utils.ElectricityHelper;
import hungpt.utils.JAXBHepler;
import hungpt.utils.StringHelper;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;

@Path("/estimate")
public class EstimateWS {

    private ProductService productService = new ProductService();
    private PriceListService priceListService = new PriceListService();
    @POST()
    @Consumes({MediaType.APPLICATION_XML})
    @Produces({MediaType.TEXT_PLAIN})
    public String calculate(Products request) {
        List<Integer> ids = request.getProduct().stream().map(Product::getId).collect(Collectors.toList());
        List<ProductEntity> productEntityList = productService.findProductByListId(ids);
        hungpt.dto.estimate.report.Products productsReport = new hungpt.dto.estimate.report.Products();
        double total = 0;
        for (Product product : request.getProduct()) {
            ProductEntity entity = productEntityList.stream().filter(p -> p.getProductId() == product.getId()).findFirst().get();
            hungpt.dto.estimate.report.Product report = new hungpt.dto.estimate.report.Product(entity.getCode(),entity.getName(),entity.getWattage(),product.getValue());
            productsReport.getProduct().add(report);
            total += report.getTotal();
        }
        ElectricityHelper.calculateByLevel(priceListService.findAllPriceList(),total);
        try {
            String currentPath = this.getClass().getClassLoader().getResource(".").getPath();
            ByteArrayOutputStream outputStream = JAXBHepler.marshall(hungpt.dto.estimate.report.Products.class,productsReport);
            String fileName = ElectricityHelper.xmlToPDF(StringHelper.deAccent(outputStream.toString()),currentPath.split("WEB-INF/classes")[0]);
            if (fileName != null) {
                return fileName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
