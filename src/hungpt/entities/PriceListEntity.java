package hungpt.entities;

import hungpt.utils.HashHepler;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "PriceList", schema = "dbo", catalog = "EstimationElectricity")
@XmlRootElement
public class PriceListEntity {
    private int priceListId;
    private int level;
    private int fromValue;
    private int toValue;
    private double rate;
    private String unit;
    private String hash;

    public PriceListEntity() {
    }

    public PriceListEntity(int level, int fromValue, int toValue, double rate, String unit) {
        this.level = level;
        this.fromValue = fromValue;
        this.toValue = toValue;
        this.rate = rate;
        this.unit = unit;
        this.hash = HashHepler.hashMD5(level+rate);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PriceListId", nullable = false)
    public int getPriceListId() {
        return priceListId;
    }

    public void setPriceListId(int priceListId) {
        this.priceListId = priceListId;
    }

    @Basic
    @Column(name = "Level", nullable = false)
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Basic
    @Column(name = "FromValue")
    public int getFrom() {
        return fromValue;
    }

    public void setFrom(int fromValue) {
        this.fromValue = fromValue;
    }

    @Basic
    @Column(name = "ToValue")
    public int getTo() {
        return toValue;
    }

    public void setTo(int toValue) {
        this.toValue = toValue;
    }

    @Basic
    @Column(name = "Rate", nullable = true, precision = 2)
    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceListEntity that = (PriceListEntity) o;
        return priceListId == that.priceListId &&
                level == that.level &&
                fromValue == that.fromValue &&
                toValue == that.toValue &&
                Objects.equals(rate, that.rate) &&
                Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(priceListId, level, fromValue, toValue, rate, unit);
    }
}
