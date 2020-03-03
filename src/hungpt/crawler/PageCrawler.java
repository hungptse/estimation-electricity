package hungpt.crawler;

import hungpt.resolver.URIResolverFU;
import hungpt.utils.CrawlHelper;
import hungpt.utils.HttpHelper;


import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class PageCrawler {
    private String url;
    private String xslPath;
    private String outputPath;

    public PageCrawler(String url, String xslPath, String outputPath) {
        this.url = url;
        this.xslPath = xslPath;
        this.outputPath = outputPath;
    }

    private void sourceToFile(ByteArrayOutputStream byteArrayOutputStream) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(this.outputPath);
        byteArrayOutputStream.writeTo(fileOutputStream);
    }

    public ByteArrayOutputStream crawl() throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String content = CrawlHelper.getWellformHTML(HttpHelper.getContent(url));
        System.out.println(content);
        //transfrom HTML(string) -> file XML bằng XSL
        StreamSource streamSource = new StreamSource(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
        TransformerFactory factory = TransformerFactory.newInstance();
        factory.setURIResolver(new URIResolverFU());
        Transformer transformer = factory.newTransformer(new StreamSource(xslPath));
        transformer.transform(streamSource, new StreamResult(os));
        sourceToFile(os);
        return os;
    }


}
