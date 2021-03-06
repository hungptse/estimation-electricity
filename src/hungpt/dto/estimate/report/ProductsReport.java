
package hungpt.dto.estimate.report;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for products complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="products">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="productReport" type="{}productReport" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "products", propOrder = {
        "createdAt",
        "total",
        "totalE",
        "product"
})
@XmlRootElement(name = "products")
public class ProductsReport {

    @XmlElement(required = true)
    protected double total;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @XmlElement(required = true)
    protected double totalE;

    public double getTotalE() {
        return totalE;
    }

    public void setTotalE(double totalE) {
        this.totalE = totalE;
    }

    @XmlElement(required = true)
    protected String createdAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @XmlElement(required = true)
    protected List<ProductReport> product;

    /**
     * Gets the value of the productReport property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the productReport property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProductReport().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductReport }
     */
    public List<ProductReport> getProductReport() {
        if (product == null) {
            product = new ArrayList<ProductReport>();
        }
        return this.product;
    }

}
