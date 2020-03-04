package hungpt.job;

import hungpt.crawler.PageCrawler;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

public class TaskCrawler extends TimerTask {
    private String name;

    private PageCrawler crawler;


    public TaskCrawler(String name, PageCrawler crawler) {
        this.name = name;
        this.crawler = crawler;
    }

    @Override
    public void run() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        System.out.println(String.format("Task [%s] executing at %s", name, formatter.format(Calendar.getInstance().getTime())));
        this.crawler.run();
        System.out.println(String.format("Task [%s] executed at %s", name, formatter.format(Calendar.getInstance().getTime())));
    }
}
