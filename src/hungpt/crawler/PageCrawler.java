package hungpt.crawler;

import hungpt.utils.CrawlHelper;
import hungpt.utils.HttpHelper;


import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class PageCrawler {
    private String url;
    private String xslPath;
    private String outputPath;
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

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public void setXslPath(String xslPath) {
        this.xslPath = xslPath;
        this.realPath = realPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public PageCrawler(String url, String realPath) {
        this.url = url;
        this.realPath = realPath;
    }

    protected void sourceToFile() throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(this.outputPath);
        this.crawl().writeTo(fileOutputStream);
    }

    protected ByteArrayOutputStream crawl() throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        TransformerFactory factory = TransformerFactory.newInstance();
        factory.setURIResolver((href, base) -> {
            try{
                String content = CrawlHelper.getWellformHTML(HttpHelper.getContent(url));
                return new StreamSource(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        });
        Transformer transformer = factory.newTransformer(new StreamSource(xslPath));
        transformer.transform(factory.getURIResolver().resolve(this.url,""), new StreamResult(os));
        return os;
    }

    public void run(){
        System.out.println("Method not implementation at " + this.url);
    }
}
