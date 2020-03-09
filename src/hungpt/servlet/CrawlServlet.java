package hungpt.servlet;

import hungpt.crawler.EvnCrawler;
import hungpt.crawler.CategoryCrawler;
import hungpt.job.TaskCrawler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class CrawlServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        String realPath = this.getServletContext().getRealPath("/");
        TaskCrawler taskCrawlerEvn = new TaskCrawler("EVN", new EvnCrawler("https://www.evn.com.vn/c3/evn-va-khach-hang/Bieu-gia-ban-le-dien-9-79.aspx", realPath));
            TaskCrawler hcCrawlerEco = new TaskCrawler("DIENMAYABC.COM", new CategoryCrawler("https://dienmayabc.com/", realPath));
        hcCrawlerEco.run();
//        taskCrawlerEvn.run();
//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(taskCrawler, 0, 1000);
    }
}
