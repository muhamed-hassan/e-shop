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
                    name = "Product.findAll",
                    query = "SELECT p.id, p.name, p.price, p.quantity, p.category.name, p.vendor.name FROM Product p WHERE p.notDeleted=:flag"
            ),
            @NamedQuery(
                    name = "Product.sortByNameASC",
                    query = "SELECT p.id, p.name, p.price, p.quantity FROM Product p WHERE p.notDeleted=:flag "
                    + "ORDER BY p.name ASC"
            ),
            @NamedQuery(
                    name = "Product.sortByNameDESC",
                    query = "SELECT p.id, p.name, p.price, p.quantity FROM Product p WHERE p.notDeleted=:flag "
                    + "ORDER BY p.name DESC"
            ),
            @NamedQuery(
                    name = "Product.sortByPriceASC",
                    query = "SELECT p.id, p.name, p.price, p.quantity FROM Product p WHERE p.notDeleted=:flag "
                    + "ORDER BY p.price ASC"
            ),
            @NamedQuery(
                    name = "Product.sortByPriceDESC",
                    query = "SELECT p.id, p.name, p.price, p.quantity FROM Product p WHERE p.notDeleted=:flag "
                    + "ORDER BY p.price DESC"
            ),
            @NamedQuery(
                    name = "Product.findByName",
                    query = "SELECT p FROM Product p WHERE p.name LIKE :pName"
            ),
            @NamedQuery(
                    name = "Product.update",
                    query = "UPDATE Product p SET p.category.Id=:catId, p.description=:desc, p.name=:pName, "
                    + "p.price=:price, p.quantity=:quantity, p.vendor.id=:vId WHERE p.id=:pId"
            ),
            @NamedQuery(
                    name = "Product.updateImg",
                    query = "UPDATE Product p SET p.image=:img WHERE p.id=:pId"
            ),
            @NamedQuery(
                    name = "Product.isExistImg",
                    query = "SELECT p.id FROM Product p WHERE ((p.id=:pId) AND (p.image IS NOT NULL))"
            ),
            @NamedQuery(
                    name = "Product.loadInstance",
                    query = "SELECT p.id, p.name, p.price, p.quantity, p.description, p.vendor, p.category "
                    + "FROM Product p WHERE p.id=:pId"
            )
        })
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Double price;

    private Integer quantity;

    @Lob
    private String description;

    @Lob
    @Column(length = 200000)
    private byte[] image;

    @Column(name = "not_deleted")
    private Boolean notDeleted = true;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "vendor")
    private Vendor vendor;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "customer_fav_product",
            joinColumns = @JoinColumn(name = "product"),
            inverseJoinColumns = @JoinColumn(name = "customer")
    )
    private List<User> interestedUsers;

    @Transient
    private Boolean imgExist;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(final byte[] image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public List<User> getInterestedUsers() {
        return interestedUsers;
    }

    public void setInterestedUsers(List<User> interestedUsers) {
        this.interestedUsers = interestedUsers;
    }

    public Boolean getNotDeleted() {
        return notDeleted;
    }

    public void setNotDeleted(Boolean notDeleted) {
        this.notDeleted = notDeleted;
    }

    public Boolean getImgExist() {
        return imgExist;
    }

    public void setImgExist(Boolean imgExist) {
        this.imgExist = imgExist;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", price=" + price + ", quantity=" + quantity + '}';
    }
}
