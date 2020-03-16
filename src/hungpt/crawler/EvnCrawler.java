package hungpt.crawler;

import hungpt.constant.EntityName;
import hungpt.constant.GlobalURL;
import hungpt.entities.PriceListEntity;
import hungpt.jaxb.evn.price.Prices;
import hungpt.repositories.MainRepository;
import hungpt.utils.JAXBHepler;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EvnCrawler extends PageCrawler {

    public EvnCrawler(String url, String realPath) {
        super(url, realPath);
        this.setXslPath(realPath + GlobalURL.XSL_EVN);
    }

    @Override
    public void run() {
        try {
            Prices prices = (Prices) JAXBHepler.unmarshall(Prices.class, this.crawl(), this.getRealPath() + GlobalURL.SCHEMA_EVN_PRICE);
            prices.getPrice().forEach(price -> {
                if (price.getFrom() == 0 && price.getTo() == 0){
                    price.setFrom(prices.getPrice().get(prices.getPrice().indexOf(price) - 1).getTo() + 1);
                    price.setTo(100000);
                }
                PriceListEntity priceListEntity = new PriceListEntity(price.getLevel(),price.getFrom(),price.getTo(), price.getRate(),"đ/kWh");
                MainRepository.getEntityByName(EntityName.PRICE_LIST_ENTITY).create(priceListEntity);
            });
        } catch (Exception e){
            Logger.getLogger(EvnCrawler.class.getName()).log(Level.SEVERE,null,e);
        }
    }
}
