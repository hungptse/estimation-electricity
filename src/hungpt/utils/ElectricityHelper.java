package hungpt.utils;

import hungpt.entities.PriceListEntity;

import java.util.List;

public class ElectricityHelper {

    public static double calculateByLevel(List<PriceListEntity> prices, double total){
        double result = 0;
        for (PriceListEntity price : prices) {
            if (total <= price.getTo()) {
                result += total * price.getRate() * 1000;
            } else {
                if (price.getFrom() != 0){
                    double levelBefore = prices.get(prices.indexOf(price) - 1).getTo();
                    result += price.getRate() * (price.getTo() - levelBefore) * 1000;
                    total -= price.getTo() - levelBefore;
                } else {
                    result += price.getRate() * (price.getTo() - price.getFrom()) * 1000;
                    total -= (price.getTo() - price.getFrom());
                }
            }
        }
        return Math.round(result);
    }
}
