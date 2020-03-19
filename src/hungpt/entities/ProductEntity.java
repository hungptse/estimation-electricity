package hungpt.entities;

import hungpt.utils.HashHepler;
import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Product", schema = "dbo", catalog = "EstimationElectricity")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "Product.findPageAndSize", query = "SELECT new hungpt.entities.ProductEntity(p.productId,p.name, p.code,p.url,p.imageLink,p.wattage) FROM ProductEntity p ORDER BY p.productId"),
        @NamedQuery(name = "Product.searchNameOrCode", query = "SELECT new hungpt.entities.ProductEntity(p.productId,p.name, p.code,p.url,p.imageLink,p.wattage) FROM ProductEntity p WHERE p.name LIKE :search OR p.code LIKE :search"),
        @NamedQuery(name = "Product.findByHash", query = "SELECT p FROM ProductEntity p WHERE p.hash = :hash")
})
public class ProductEntity implements Serializable {
    private int productId;
    private String name;
    private String code;
    private double wattage;
    private String unit;
    private String hash;
    private String url;
    private String imageLink;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isPrivateProduct;
    private CategoryEntity categoryEntity;

    public ProductEntity() {

    }

    public ProductEntity(int productId, String name, String code, String url, String imageLink, double wattage) {
        this.productId = productId;
        this.name = name;
        this.code = code;
        this.wattage = wattage;
        this.url = url;
        this.imageLink = imageLink;
    }

    public ProductEntity(String name, String code, double wattage, String url, String imageLink) {
        this.name = name;
        this.code = code;
        this.wattage = wattage;
        this.url = url;
        this.imageLink = imageLink;
        this.hash = HashHepler.hashMD5(name.replaceAll(" ", "") + code.replaceAll(" ", ""));
        this.createdAt = new Date(System.currentTimeMillis());
        this.updatedAt = new Date(System.currentTimeMillis());
        this.unit = "W";
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
    @Column(name = "Name", nullable = false, length = 500)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Code", nullable = false, length = 50)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "Wattage", nullable = true)
    public double getWattage() {
        return wattage;
    }

    public void setWattage(double wattage) {
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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "CateId")
    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    @Basic
    @Column(name = "CreatedAt", nullable = true)
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "UpdatedAt", nullable = true)
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
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
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt) &&
                Objects.equals(isPrivateProduct, that.isPrivateProduct);
    }

}
