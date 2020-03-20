package hungpt.utils;

import hungpt.constant.GlobalURL;
import hungpt.entities.PriceListEntity;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.xml.sax.SAXException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
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

    public static void xmlToPDF(String content) throws IOException, SAXException {
        System.out.println(content);
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        OutputStream out = new BufferedOutputStream(new FileOutputStream(new File("/Users/jeremy/Documents/PRX-XML/EstimationElectricity/out/artifacts/EstimationElectricity/" + "WEB-INF/test.pdf")));
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        try{

            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource("/Users/jeremy/Documents/PRX-XML/EstimationElectricity/out/artifacts/EstimationElectricity/"+ GlobalURL.XSL_REPORT));
            Source src = new StreamSource(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
            Result res = new SAXResult(fop.getDefaultHandler());

            transformer.transform(src, res);

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}
