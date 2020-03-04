package hungpt.crawler;

import hungpt.constant.GlobalURL;
import hungpt.jaxb.eco.Products;
import hungpt.utils.JAXBHepler;

public class TestCrawler implements Runnable {
    private int page;
    private EcoCrawler crawler;
    private Thread thread;
    public TestCrawler(int page, EcoCrawler crawler) {
        this.page = page;
        this.crawler = crawler;
    }

    @Override
    public void run() {
        System.out.println("START page + " + this.page + " at" + System.currentTimeMillis());
        synchronized(crawler)
        {
            String baseUrl = this.crawler.getUrl();
            try {
                this.crawler.setUrl(baseUrl + "?page=" + this.page);
                Products products = (Products) JAXBHepler.unmarshall(Products.class, this.crawler.crawl(), this.crawler.getRealPath() + GlobalURL.SCHEMA_ECO);

                System.out.println(products.getProduct().size() + " " + this.crawler.getUrl());
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                this.crawler.setUrl(baseUrl);
            }
        }
        System.out.println("END" + System.currentTimeMillis());
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }
}
