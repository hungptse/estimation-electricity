package hungpt.servlet;

import com.sun.net.httpserver.HttpServer;
import hungpt.constant.EntityName;
import hungpt.constant.TimeConst;
import hungpt.crawler.CategoryCrawler;
import hungpt.crawler.EvnCrawler;
import hungpt.crawler.ProductCrawler;
import hungpt.entities.ProductEntity;
import hungpt.job.TaskTimer;
import hungpt.repositories.MainRepository;
import hungpt.ws.ApplicationConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Timer;

@WebServlet(name = "CrawlServlet",urlPatterns = "/crawl")
public class CrawlServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String realPath = this.getServletContext().getRealPath("/");
        TaskTimer evnCrawler = new TaskTimer("EVN", new EvnCrawler("https://www.evn.com.vn/c3/evn-va-khach-hang/Bieu-gia-ban-le-dien-9-79.aspx", realPath));
        TaskTimer abcCrawler = new TaskTimer("DIENMAYABC.COM", new CategoryCrawler("https://dienmayabc.com/", realPath));
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(abcCrawler, 1000, TimeConst.HOUR_IN_MILLISECOND);
        timer.scheduleAtFixedRate(evnCrawler, 0, TimeConst.HOUR_IN_MILLISECOND);
    }
}
