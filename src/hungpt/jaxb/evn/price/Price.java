
package hungpt.jaxb.evn.price;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for price complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="price">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="level" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *         &lt;element name="from" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *         &lt;element name="to" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *         &lt;element name="rate" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "price", namespace = "https://www.evn.com.vn/", propOrder = {
    "level",
    "from",
    "to",
    "rate"
})
public class Price {

    @XmlElement(namespace = "https://www.evn.com.vn/", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected int level;
    @XmlElement(namespace = "https://www.evn.com.vn/")
    @XmlSchemaType(name = "positiveInteger")
    protected int from;
    @XmlElement(namespace = "https://www.evn.com.vn/")
    @XmlSchemaType(name = "positiveInteger")
    protected int to;
    @XmlElement(namespace = "https://www.evn.com.vn/", required = true)
    @XmlSchemaType(name = "double")
    protected double rate;

    /**
     * Gets the value of the level property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the value of the level property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLevel(int value) {
        this.level = value;
    }

    /**
     * Gets the value of the from property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public int getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setFrom(int value) {
        this.from = value;
    }

    /**
     * Gets the value of the to property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public int getTo() {
        return to;
    }

    /**
     * Sets the value of the to property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTo(int value) {
        this.to = value;
    }

    /**
     * Gets the value of the rate property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public double getRate() {
        return rate;
    }

    /**
     * Sets the value of the rate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRate(double value) {
        this.rate = value;
    }

}
