package hungpt.services;

import hungpt.entities.PriceListEntity;
import hungpt.repositories.PriceListRepository;

import java.util.List;

public class PriceListService {
    PriceListRepository priceListRepository = new PriceListRepository();

    public List<PriceListEntity> findAllPriceList(){
        return priceListRepository.findMany("PriceList.findAll",null);
    }
}
