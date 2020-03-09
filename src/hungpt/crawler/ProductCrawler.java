package hungpt.crawler;

import hungpt.constant.GlobalURL;
import hungpt.jaxb.dienmayabc.product.Product;
import hungpt.utils.HashHepler;
import hungpt.utils.JAXBHepler;
import hungpt.utils.StringHelper;

public class ProductCrawler extends PageCrawler {
    private String cateName;
    private static final String HOST_ABC = "https://dienmayabc.com/";
    public ProductCrawler(String url, String realPath, String cateName) {
        super(url, realPath);
        this.setUrl(HOST_ABC +  url);
        this.setXslPath(realPath + GlobalURL.XSL_ABC_PRODUCT_DETAIL);
        this.cateName = cateName;
    }

    @Override
    public void run() {
        try{
            Product product = (Product) JAXBHepler.unmarshall(Product.class, this.crawl(), this.getRealPath() + GlobalURL.SCHEMA_ABC_CATEGORY);
//            System.out.println(this.getUrl());
            double validWattage = Math.round(StringHelper.getValidWattage(product.getWattage()));
            if (validWattage != 0){
                System.out.println(HashHepler.hashMD5(product.toString()));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
