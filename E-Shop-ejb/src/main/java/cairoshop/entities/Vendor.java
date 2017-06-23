package cairoshop.entities;

import java.io.*;
import java.util.*;
import javax.persistence.*;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Entity
@NamedQueries(
        {
            @NamedQuery(
                    name = "Vendor.findAll",
                    query = "SELECT v FROM Vendor v WHERE v.notDeleted=:flag"
            ),
            @NamedQuery(
                    name = "Vendor.countProducts",
                    query = "SELECT COUNT(p.id) FROM Product p WHERE ((p.vendor.id=:vId) "
                    + "AND (p.notDeleted=:flag))"
            ),
            @NamedQuery(
                    name = "Vendor.getProducts",
                    query = "SELECT p.id, p.name, p.price, p.quantity FROM Product p WHERE ((p.vendor.id=:venId) "
                    + "AND (p.notDeleted=:flag))"
            )
        })
public class Vendor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String name;

    @Column(name = "not_deleted")
    private Boolean notDeleted = true;

    @OneToMany(mappedBy = "vendor")
    private List<Product> products;

    @Transient
    private Boolean canEdit = false;

    @Transient
    private String oldValue;

    public Vendor() {
    }

    public Vendor(String name) {
        this.name = name;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getNotDeleted() {
        return notDeleted;
    }

    public void setNotDeleted(Boolean notDeleted) {
        this.notDeleted = notDeleted;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    @Override
    public String toString() {
        return "Vendor{" + "Id=" + Id + ", name=" + name + ", notDeleted=" + notDeleted + '}';
    }

}
