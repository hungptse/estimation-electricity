package hungpt.ws;

import hungpt.dto.estimate.req.Product;
import hungpt.dto.estimate.req.Products;
import hungpt.entities.ProductEntity;
import hungpt.repositories.PriceListRepository;
import hungpt.repositories.ProductRepository;
import hungpt.services.PriceListService;
import hungpt.services.ProductService;
import hungpt.utils.ElectricityHelper;
import hungpt.utils.JAXBHepler;
import hungpt.utils.StringHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/estimate")
public class EstimateWS {

    private ProductService productService = new ProductService();
    private PriceListService priceListService = new PriceListService();
    @POST()
    @Consumes({MediaType.APPLICATION_XML})
    public void calculate(Products request) {
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
        productsReport.getProduct();
        try {
            ByteArrayOutputStream outputStream = JAXBHepler.marshall(hungpt.dto.estimate.report.Products.class,productsReport);
            ElectricityHelper.xmlToPDF(StringHelper.deAccent(outputStream.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(ElectricityHelper.calculateByLevel(priceListService.findAllPriceList(),total));
    }
}
