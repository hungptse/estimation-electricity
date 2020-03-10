
package hungpt.jaxb.evn.price;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for prices complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="prices">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="price" type="{https://www.evn.com.vn/}price" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "prices", namespace = "https://www.evn.com.vn/", propOrder = {
    "price"
})
@XmlRootElement(name = "prices")
public class Prices {

    @XmlElement(namespace = "https://www.evn.com.vn/", required = true)
    protected List<Price> price;

    /**
     * Gets the value of the price property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the price property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Price }
     * 
     * 
     */
    public List<Price> getPrice() {
        if (price == null) {
            price = new ArrayList<Price>();
        }
        return this.price;
    }

}
