package hungpt.servlet;

import hungpt.constant.TimeConst;
import hungpt.crawler.EvnCrawler;
import hungpt.crawler.CategoryCrawler;
import hungpt.job.TaskTimer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.Timer;

public class CrawlServlet extends HttpServlet {

    @Override
    public void init() {
        String realPath = this.getServletContext().getRealPath("/");
        TaskTimer evnCrawler = new TaskTimer("EVN", new EvnCrawler("https://www.evn.com.vn/c3/evn-va-khach-hang/Bieu-gia-ban-le-dien-9-79.aspx", realPath));
        TaskTimer abcCrawler = new TaskTimer("DIENMAYABC.COM", new CategoryCrawler("https://dienmayabc.com/", realPath));
//        hcCrawlerEco.run();
//        taskCrawlerEvn.run();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(abcCrawler, 0, TimeConst.HOUR_IN_MILLISECOND);
        timer.scheduleAtFixedRate(evnCrawler, 0, TimeConst.HOUR_IN_MILLISECOND);
    }
}
