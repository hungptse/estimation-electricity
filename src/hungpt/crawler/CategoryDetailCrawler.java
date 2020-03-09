package hungpt.crawler;

import hungpt.constant.GlobalURL;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryDetailCrawler extends PageCrawler {
    private List<String> productListUrl = new ArrayList();
    private String name;

    public CategoryDetailCrawler(String url, String realPath, String name) {
        super(url, realPath);
        this.setXslPath(this.getRealPath() + GlobalURL.XSL_ABC_PRODUCT_MAXPAGE);
        this.name = name;
    }

    public List<String> getProductListUrl() {
        return productListUrl;
    }


    @Override
    public void run() {
        try {
            int maxPage = 1;
            if (!this.crawl().toString().equals("")) {
                maxPage = Integer.parseInt(this.crawl().toString());
            }
            this.setXslPath(this.getRealPath() + GlobalURL.XSL_ABC_PRODUCT_LINK);
            for (int i = 1; i <= maxPage; i++) {
                String listUrl = this.crawl().toString();
                productListUrl.addAll(Arrays.asList(listUrl.split("/")));
                Arrays.asList(listUrl.split("/")).forEach(url -> {
                    if (!url.equals("")){
                        ProductCrawler productCrawler = new ProductCrawler(url, getRealPath(), this.name);
                        productCrawler.run();
                    }
                });
            }
            System.out.println(this.getUrl() + " " + maxPage + " pages. Has " + productListUrl.size() + " records");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
