package hungpt.crawler;

import hungpt.constant.GlobalURL;
import hungpt.entities.CategoryEntity;
import hungpt.jaxb.dienmayabc.category.Categories;
import hungpt.repositories.CategoryRepository;
import hungpt.utils.HashHepler;
import hungpt.utils.JAXBHepler;

import java.util.logging.Level;
import java.util.logging.Logger;


public class CategoryCrawler extends PageCrawler {
    private static CategoryRepository categoryRepository = new CategoryRepository();
    public CategoryCrawler(String url, String realPath) {
        super(url, realPath);

        this.setXslPath(this.getRealPath() + GlobalURL.XSL_ABC_CATEGORY);
    }


    @Override
    public void run() {

        try {
            Categories categories = (Categories) JAXBHepler.unmarshall(Categories.class, this.crawl(), this.getRealPath() + GlobalURL.SCHEMA_ABC_CATEGORY);
            categories.getCategory().stream().forEach(category -> {
                CategoryEntity categoryEntity = categoryRepository.getCategoryByHash(HashHepler.hashMD5(category.getName()));
                if (categoryEntity == null){
                    categoryEntity = new CategoryEntity(category.getName(),category.getUrl());
                }
                CategoryDetailCrawler detailCrawler = new CategoryDetailCrawler(category.getUrl(),this.getRealPath(),categoryEntity);
                detailCrawler.run();
            });
        } catch (Exception e) {
            Logger.getLogger(CategoryCrawler.class.getName()).log(Level.SEVERE,e.getMessage(), e);
        }
    }
}
