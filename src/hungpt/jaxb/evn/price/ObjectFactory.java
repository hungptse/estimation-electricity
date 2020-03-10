
package hungpt.jaxb.evn.price;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the hungpt.jaxb.evn.price package. 
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

    private final static QName _Prices_QNAME = new QName("https://www.evn.com.vn/", "prices");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: hungpt.jaxb.evn.price
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Prices }
     * 
     */
    public Prices createPrices() {
        return new Prices();
    }

    /**
     * Create an instance of {@link Price }
     * 
     */
    public Price createPrice() {
        return new Price();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Prices }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://www.evn.com.vn/", name = "prices")
    public JAXBElement<Prices> createPrices(Prices value) {
        return new JAXBElement<Prices>(_Prices_QNAME, Prices.class, null, value);
    }

}
