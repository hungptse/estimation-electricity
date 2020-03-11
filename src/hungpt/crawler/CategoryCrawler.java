package hungpt.crawler;

import hungpt.constant.GlobalURL;
import hungpt.jaxb.dienmayabc.category.Categories;
import hungpt.jaxb.dienmayabc.category.Category;
import hungpt.utils.JAXBHepler;

public class CategoryCrawler extends PageCrawler {

    private int count = 0;

    public CategoryCrawler(String url, String realPath) {
        super(url, realPath);

        this.setXslPath(this.getRealPath() + GlobalURL.XSL_ABC_CATEGORY);
    }


    @Override
    public void run() {
        try {
            Categories categories = (Categories) JAXBHepler.unmarshall(Categories.class, this.crawl(), this.getRealPath() + GlobalURL.SCHEMA_ABC_CATEGORY);
            categories.getCategory().stream().forEach(category -> {
                CategoryDetailCrawler detailCrawler = new CategoryDetailCrawler(category.getUrl(),this.getRealPath(),category.getName());
                detailCrawler.run();
                count = count + detailCrawler.getProductListUrl().size();
            });
            System.out.println("Total : " + count + " records");
            System.out.println("ProductWS avaliable " + ProductCrawler.getProductList().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
