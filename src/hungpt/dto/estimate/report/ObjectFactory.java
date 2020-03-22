
package hungpt.dto.estimate.report;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the hungpt.dto.estimate.report package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Products_QNAME = new QName("", "products");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: hungpt.dto.estimate.report
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ProductsReport }
     * 
     */
    public ProductsReport createProducts() {
        return new ProductsReport();
    }

    /**
     * Create an instance of {@link ProductReport }
     * 
     */
    public ProductReport createProduct() {
        return new ProductReport();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProductsReport }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "products")
    public JAXBElement<ProductsReport> createProducts(ProductsReport value) {
        return new JAXBElement<ProductsReport>(_Products_QNAME, ProductsReport.class, null, value);
    }

}
