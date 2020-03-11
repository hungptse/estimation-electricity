package hungpt.entities;

import hungpt.utils.HashHepler;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Category", schema = "dbo", catalog = "EstimationElectricity")
@XmlRootElement
public class CategoryEntity implements Serializable {
    private int cateId;
    private String cateName;
    private String cateLink;
    private String hash;

    public CategoryEntity() {
    }

    public CategoryEntity(String cateName, String cateLink) {
        this.cateName = cateName;
        this.cateLink = cateLink;
        this.hash = HashHepler.hashMD5(cateName);
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
}
