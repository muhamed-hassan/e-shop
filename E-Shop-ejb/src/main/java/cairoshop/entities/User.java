package cairoshop.entities;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
        hash = 89 * hash + Objects.hashCode(this.username);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return true;
    }  
    
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", userName=" + username + ", mail=" + email + ", active=" + active + '}';
    }

}
