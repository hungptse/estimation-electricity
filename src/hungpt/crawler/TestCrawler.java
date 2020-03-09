package hungpt.crawler;

import hungpt.constant.GlobalURL;
import hungpt.utils.JAXBHepler;

public class TestCrawler implements Runnable {
    private int page;
    private Thread thread;

    @Override
    public void run() {
        System.out.println("START page + " + this.page + " at" + System.currentTimeMillis());
//        synchronized(crawler)
//        {
//            String baseUrl = this.crawler.getUrl();
//            try {
//                this.crawler.setUrl(baseUrl + "?page=" + this.page);
//                Products products = (Products) JAXBHepler.unmarshall(Products.class, this.crawler.crawl(), this.crawler.getRealPath() + GlobalURL.SCHEMA_ECO);
//
//                System.out.println(products.getProduct().size() + " " + this.crawler.getUrl());
//
//            } catch (Exception e){
//                e.printStackTrace();
//            } finally {
//                this.crawler.setUrl(baseUrl);
//            }
//        }
        System.out.println("END" + System.currentTimeMillis());
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }
}
