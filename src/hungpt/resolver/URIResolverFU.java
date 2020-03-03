package hungpt.resolver;

import hungpt.utils.CrawlHelper;
import hungpt.utils.HttpHelper;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class URIResolverFU implements URIResolver {
    private int count = 0;
    @Override
    public Source resolve(String href, String base) throws TransformerException {
        System.out.println(href.indexOf("https://hcmuni.fpt.edu.vn/tin-tuc") == 0);
        if (href != null && href.indexOf("https://hcmuni.fpt.edu.vn/tin-tuc") == 0) {
            try {
                String content = CrawlHelper.getWellformHTML(HttpHelper.getContent(href));
                System.out.println("Count: " + ++count);
                return new StreamSource(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
        return null;
    }
}
