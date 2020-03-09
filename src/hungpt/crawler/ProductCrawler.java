package hungpt.crawler;

import hungpt.constant.GlobalURL;
import hungpt.jaxb.dienmayabc.product.Product;
import hungpt.utils.HashHepler;
import hungpt.utils.JAXBHepler;
import hungpt.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductCrawler extends PageCrawler implements Runnable {
    private Thread thread;
    private String cateName;
    private static final String HOST_ABC = "https://dienmayabc.com/";
    public ProductCrawler(String url, String realPath, String cateName) {
        super(url, realPath);
        this.setUrl(HOST_ABC +  url);
        this.setXslPath(realPath + GlobalURL.XSL_ABC_PRODUCT_DETAIL);
        this.cateName = cateName;
    }

    private static List<Product> productList = new ArrayList<>();
    @Override
    public void run() {
        try{
            Product product = (Product) JAXBHepler.unmarshall(Product.class, this.crawl(), this.getRealPath() + GlobalURL.SCHEMA_ABC_CATEGORY);
            double validWattage = Math.round(StringHelper.getValidWattage(product.getWattage()));
            if (validWattage != 0){
                this.productList.add(product);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static List<Product> getProductList() {
        return productList;
    }


    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }
}
