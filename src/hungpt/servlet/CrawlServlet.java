package hungpt.servlet;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import com.sun.xml.internal.stream.events.XMLEventAllocatorImpl;
import hungpt.constant.GlobalURL;
import hungpt.crawler.PageCrawler;
import hungpt.job.TaskCrawler;
import hungpt.utils.CrawlHelper;
import hungpt.utils.HttpHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.util.XMLEventAllocator;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class CrawlServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        String realPath = this.getServletContext().getRealPath("/");
        PageCrawler evnCrawler = new PageCrawler("https://www.evn.com.vn/c3/evn-va-khach-hang/Bieu-gia-ban-le-dien-9-79.aspx", realPath + GlobalURL.XSL_EVN, realPath + GlobalURL.OUTPUT_EVN);
        TaskCrawler taskCrawler = new TaskCrawler("EVN", evnCrawler);
        taskCrawler.run();
//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(taskCrawler, 0, 1000);
    }
}
