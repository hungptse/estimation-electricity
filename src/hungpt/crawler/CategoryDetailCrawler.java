package hungpt.crawler;

import hungpt.constant.GlobalURL;
import hungpt.entities.CategoryEntity;
import hungpt.repositories.CategoryRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryDetailCrawler extends PageCrawler {
    private List<String> productListUrl = new ArrayList();
    private CategoryEntity categoryEntity;
    private static CategoryRepository categoryRepository = new CategoryRepository();
    public CategoryDetailCrawler(String url, String realPath, CategoryEntity categoryEntity) {
        super(url, realPath);
        this.setXslPath(this.getRealPath() + GlobalURL.XSL_ABC_PRODUCT_MAXPAGE);
        this.categoryEntity = categoryEntity;
    }

    @Override
    public void run() {
        try {
            int beforeCrawl = categoryEntity.getProductEntityList().size();
            int maxPage = 1;
            if (!this.crawl().toString().equals("")) {
                maxPage = Integer.parseInt(this.crawl().toString());
            }
            this.setXslPath(this.getRealPath() + GlobalURL.XSL_ABC_PRODUCT_LINK);
            String baseUrl = this.getUrl();
            for (int i = 1; i <= maxPage; i++) {
                this.setUrl(baseUrl + "?page=" + i);
                String listUrl = this.crawl().toString();
                productListUrl.addAll(Arrays.asList(listUrl.split("/")));
            }
            productListUrl.forEach(url -> {
                if (!url.equals("")) {
                    ProductCrawler productCrawler = new ProductCrawler(url, getRealPath());
                    productCrawler.run();
                    if (productCrawler.getProductEntity() != null) {
                        if (!categoryEntity.getProductEntityList().stream().anyMatch(p -> p.getHash().equals(productCrawler.getProductEntity().getHash()))) {
                            categoryEntity.addProduct(productCrawler.getProductEntity());
                        }
                    }
                }
            });
            System.out.println(this.getUrl() + " " + maxPage + " pages. Has " + (categoryEntity.getProductEntityList().size() - beforeCrawl) + " new records");
            categoryRepository.create(categoryEntity);
        } catch (Exception e) {
            Logger.getLogger(CategoryDetailCrawler.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
