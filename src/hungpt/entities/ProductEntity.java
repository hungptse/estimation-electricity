package hungpt.entities;

import hungpt.utils.HashHepler;
import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Product", schema = "dbo", catalog = "EstimationElectricity")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "Product.findPageAndSize" , query = "SELECT p FROM ProductEntity p ORDER BY p.productId")})
public class ProductEntity implements Serializable {
    private int productId;
    private String name;
    private String code;
    private BigDecimal wattage;
    private String unit;
    private String hash;
    private String url;
    private String imageLink;
    private Integer cateId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Boolean isPrivateProduct;

    public ProductEntity() {

    }

    public ProductEntity(String name, String code, BigDecimal wattage, String url, String imageLink) {
        this.name = name;
        this.code = code;
        this.wattage = wattage;
        this.url = url;
        this.imageLink = imageLink;
        this.hash = HashHepler.hashMD5(name.replaceAll(" ","") + code.replaceAll(" ",""));

    }

    @Id
    @Column(name = "ProductId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "Name", nullable = true, length = 500)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Code", nullable = true, length = 50)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "Wattage", nullable = true, precision = 2)
    public BigDecimal getWattage() {
        return wattage;
    }

    public void setWattage(BigDecimal wattage) {
        this.wattage = wattage;
    }

    @Basic
    @Column(name = "Unit", nullable = true, length = 10)
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "Hash", length = 500)
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Basic
    @Column(name = "Url", nullable = true, length = 500)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "ImageLink", nullable = true, length = 500)
    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @Basic
    @Column(name = "CateId", nullable = true)
    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    @Basic
    @Column(name = "CreatedAt", nullable = true)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "UpdatedAt", nullable = true)
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Basic
    @Column(name = "isPrivateProduct", nullable = true)
    public Boolean getPrivateProduct() {
        return isPrivateProduct;
    }

    public void setPrivateProduct(Boolean privateProduct) {
        isPrivateProduct = privateProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return productId == that.productId &&
                Objects.equals(name, that.name) &&
                Objects.equals(code, that.code) &&
                Objects.equals(wattage, that.wattage) &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(hash, that.hash) &&
                Objects.equals(url, that.url) &&
                Objects.equals(imageLink, that.imageLink) &&
                Objects.equals(cateId, that.cateId) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt) &&
                Objects.equals(isPrivateProduct, that.isPrivateProduct);
    }

}
