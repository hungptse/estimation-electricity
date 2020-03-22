package hungpt.crawler;

import hungpt.constant.GlobalURL;
import hungpt.entities.PriceListEntity;
import hungpt.jaxb.evn.price.Prices;
import hungpt.repositories.PriceListRepository;
import hungpt.utils.HashHepler;
import hungpt.utils.JAXBHepler;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EvnCrawler extends PageCrawler {

    public EvnCrawler(String url, String realPath) {
        super(url, realPath);
        this.setXslPath(realPath + GlobalURL.XSL_EVN);
    }

    @Override
    public void run() {
        PriceListRepository priceListRepository = new PriceListRepository();
        try {
            Prices prices = (Prices) JAXBHepler.unmarshall(Prices.class, this.crawl(), this.getRealPath() + GlobalURL.SCHEMA_EVN_PRICE);
            prices.getPrice().forEach(price -> {
                if (price.getFrom() == 0 && price.getTo() == 0){
                    price.setFrom(prices.getPrice().get(prices.getPrice().indexOf(price) - 1).getTo() + 1);
                    price.setTo(100000);
                }
                if (priceListRepository.getPriceListByHash(HashHepler.hashMD5(price.getLevel() + "|" + price.getRate())) == null){
                    PriceListEntity priceListEntity = new PriceListEntity(price.getLevel(),price.getFrom(),price.getTo(), price.getRate(),"đ/kWh");
                    priceListRepository.create(priceListEntity);
                }
            });
        } catch (Exception e){
            Logger.getLogger(EvnCrawler.class.getName()).log(Level.SEVERE,null,e);
        }
    }
}
