package hungpt.crawler;

import hungpt.constant.GlobalURL;

public class EvnCrawler extends PageCrawler {

    public EvnCrawler(String url, String realPath) {
        super(url, realPath);
        this.setXslPath(realPath + GlobalURL.XSL_EVN);
        this.setOutputPath(realPath + GlobalURL.OUTPUT_EVN);
    }

    @Override
    public void run() {
        try {
            System.out.println(this.crawl());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
