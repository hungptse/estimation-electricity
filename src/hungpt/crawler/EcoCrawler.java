package hungpt.crawler;

import hungpt.constant.GlobalURL;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EcoCrawler extends PageCrawler {

    public EcoCrawler(String url, String realPath) {
        super(url, realPath);
        this.setXslPath(realPath + GlobalURL.XSL_ECO_MAXPAGE);
        this.setOutputPath(realPath + GlobalURL.OUTPUT_ECO);
    }

    List<String> listUrlProduct = new ArrayList<>();

    @Override
    public void run() {
        try {
            String baseUrl = this.getUrl();
            int maxPage = Integer.parseInt(this.crawl().toString());
            this.setXslPath(this.getRealPath() + GlobalURL.XSL_ECO);
            for (int i = 1; i <= maxPage; i++) {
                setUrl(baseUrl + "?page=" + i);
                System.out.println("Crawling page " + getUrl());
                getURLProduct();
                setUrl(baseUrl);
            }
            System.out.println("Collect total [" + this.listUrlProduct.size() + "] records");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getURLProduct() throws Exception {
        sourceToFile();
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(getRealPath() + GlobalURL.OUTPUT_ECO));
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = String.format("//product/url");
        if (document != null) {
            NodeList listUrl = (NodeList) xPath.evaluate(expression, document, XPathConstants.NODESET);
            if (listUrl != null){
                for (int i = 0; i < listUrl.getLength(); i++) {
                    Node node = listUrl.item(i);
                    listUrlProduct.add(node.getTextContent());
                }
            }
        }
    }
}
