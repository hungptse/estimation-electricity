package hungpt.crawler;

import hungpt.utils.CrawlHelper;
import hungpt.utils.HttpHelper;


import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class PageCrawler {
    private String url;
    private String xslPath;
    private String realPath;
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getXslPath() {
        return xslPath;
    }

    public String getRealPath() {
        return realPath;
    }


    public void setXslPath(String xslPath) {
        this.xslPath = xslPath;
    }


    public PageCrawler(String url, String realPath) {
        this.url = url;
        this.realPath = realPath;
    }

    protected ByteArrayOutputStream crawl() throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        TransformerFactory factory = TransformerFactory.newInstance();
        factory.setURIResolver((href, base) -> {
            try{
                String content = CrawlHelper.getWellformHTML(HttpHelper.getContent(href));
                return new StreamSource(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
            } catch (IOException e){
                Logger.getLogger(PageCrawler.class.getName()).log(Level.SEVERE,null,e);
            }
            return null;
        });
        Transformer transformer = factory.newTransformer(new StreamSource(xslPath));
        Source source = factory.getURIResolver().resolve(this.url,"");
        if (source != null){
            transformer.transform(factory.getURIResolver().resolve(this.url,""), new StreamResult(os));
        }
        return os;
    }

    public abstract void run();
}
