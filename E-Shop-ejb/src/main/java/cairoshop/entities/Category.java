package cairoshop.entities;

import java.io.*;
import java.util.*;
import javax.persistence.*;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Entity
@NamedQueries(
        {
            @NamedQuery(
                    name = "Category.countProducts",
                    query = "SELECT COUNT(p.id) FROM Product p WHERE ((p.category.id=:cId) "
                    + "AND (p.notDeleted=:flag))"
            ),
            @NamedQuery(
                    name = "Category.getProducts",
                    query = "SELECT p.id, p.name, p.price, p.quantity FROM Product p WHERE ((p.category.id=:catId) "
                    + "AND (p.notDeleted=:flag))"
            )
        })
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String name;

    @Column(name = "not_deleted")
    private Boolean notDeleted = true;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @Transient
    private Boolean canEdit = false;

    @Transient
    private String oldValue;

    public Category() {
    }

    public Category(String name) {
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
        return "Category{" + "Id=" + Id + ", name=" + name + ", notDeleted=" + notDeleted + '}';
    }
}
