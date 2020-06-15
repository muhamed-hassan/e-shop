package cairoshop.entities;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Entity
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
public abstract class User implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    private String name;

    @Column(name = "user_name")
    private String username;

    @Column(name = "mail")
    private String email;

    private String password;

    private boolean active;
    
    @ManyToMany(fetch = LAZY)
    @JoinTable(name = "customer_fav_product",
                joinColumns = @JoinColumn(name = "customer"),
                inverseJoinColumns = @JoinColumn(name = "product")
    )
    private List<Product> favoriteProducts;
 
    @Column(insertable=false, updatable=false)
    private int role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Product> getFavoriteProducts() {
        return favoriteProducts;
    }

    public void setFavoriteProducts(List<Product> favoriteProducts) {
        this.favoriteProducts = favoriteProducts;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
   }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        User that = (User) object;
        return new EqualsBuilder()
                    .append(id, that.id)
                    .append(active, that.active)
                    .append(name, that.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                    .append(id)
                    .append(name)
                    .append(active)
                .toHashCode();
    }

}
