package hungpt.crawler;

import hungpt.constant.EntityName;
import hungpt.constant.GlobalURL;
import hungpt.entities.PriceListEntity;
import hungpt.jaxb.dienmayabc.product.Product;
import hungpt.jaxb.evn.price.Price;
import hungpt.jaxb.evn.price.Prices;
import hungpt.repositories.MainRepository;
import hungpt.utils.JAXBHepler;

import java.math.BigDecimal;
import java.math.BigInteger;

public class EvnCrawler extends PageCrawler {

    public EvnCrawler(String url, String realPath) {
        super(url, realPath);
        this.setXslPath(realPath + GlobalURL.XSL_EVN);
        this.setOutputPath(realPath + GlobalURL.OUTPUT_EVN);
    }

    @Override
    public void run() {
        try {
            Prices prices = (Prices) JAXBHepler.unmarshall(Prices.class, this.crawl(), this.getRealPath() + GlobalURL.SCHEMA_EVN_PRICE);
            prices.getPrice().forEach(price -> {

                MainRepository.getEntityByName(EntityName.PRICE_LIST_ENTITY).create(new PriceListEntity(price.getLevel(),price.getFrom(),price.getTo(), price.getRate(),"đ/kWh"));
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
