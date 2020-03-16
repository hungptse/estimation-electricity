package hungpt.crawler;

import hungpt.constant.EntityName;
import hungpt.constant.GlobalURL;
import hungpt.entities.CategoryEntity;
import hungpt.entities.ProductEntity;
import hungpt.jaxb.dienmayabc.product.Product;
import hungpt.repositories.MainRepository;
import hungpt.repositories.ProductRepository;
import hungpt.utils.HashHepler;
import hungpt.utils.JAXBHepler;
import hungpt.utils.StringHelper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductCrawler extends PageCrawler{
    private ProductEntity productEntity;
    private static final String HOST_ABC = "https://dienmayabc.com/";

    public ProductCrawler(String url, String realPath) {
        super(url, realPath);
        this.setUrl(HOST_ABC + url);
        this.setXslPath(realPath + GlobalURL.XSL_ABC_PRODUCT_DETAIL);
    }

    @Override
    public void run() {
        ProductRepository productRepository = new ProductRepository();
        try {
            Product product = (Product) JAXBHepler.unmarshall(Product.class, this.crawl(), this.getRealPath() + GlobalURL.SCHEMA_ABC_PRODUCT);
            double validWattage = Math.round(StringHelper.getValidWattage(product.getWattage()));
            ProductEntity productEntity = productRepository.findProductByHash(HashHepler.hashMD5(product.getName().replaceAll(" ", "") + product.getCode().replaceAll(" ", "")));
            if (productEntity == null){
                productEntity = new ProductEntity(product.getName(),product.getCode(),validWattage,this.getUrl(),product.getImage());
                productEntity.setPrivateProduct(false);
            }
            this.productEntity = productEntity;
        } catch (Exception e) {
            Logger.getLogger(ProductCrawler.class.getName()).log(Level.SEVERE,null, e);
        }

    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

}
