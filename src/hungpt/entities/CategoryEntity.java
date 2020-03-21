package hungpt.entities;

import hungpt.utils.HashHepler;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Category", schema = "dbo", catalog = "EstimationElectricity")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Category.findByHash",query = "SELECT c FROM CategoryEntity c WHERE c.hash = :hash")
})
public class CategoryEntity implements Serializable {
    private int cateId;
    private String cateName;
    private String cateLink;
    private String hash;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<ProductEntity> productEntityList = new ArrayList<>();

    public CategoryEntity() {
    }

    public CategoryEntity(String cateName, String cateLink) {
        this.cateName = cateName;
        this.cateLink = cateLink;
        this.hash = HashHepler.hashMD5(cateName);
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CateId", nullable = false)
    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    @Basic
    @Column(name = "CateName", nullable = false, length = 50)
    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    @Basic
    @Column(name = "CateLink", nullable = false, length = 50)
    public String getCateLink() {
        return cateLink;
    }

    public void setCateLink(String cateLink) {
        this.cateLink = cateLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryEntity that = (CategoryEntity) o;
        return cateId == that.cateId &&
                Objects.equals(cateName, that.cateName) &&
                Objects.equals(cateLink, that.cateLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cateId, cateName, cateLink);
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

    @OneToMany(mappedBy = "categoryEntity")
    public List<ProductEntity> getProductEntityList() {
        return productEntityList;
    }

    private void setProductEntityList(List<ProductEntity> productEntityList) {
        this.productEntityList = productEntityList;
    }

    public void addProduct(ProductEntity entity){
        this.productEntityList.add(entity);
        entity.setCategoryEntity(this);
    }
}
