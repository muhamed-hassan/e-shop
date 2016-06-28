package cairoshop.entities;

import javax.persistence.*;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Entity
@DiscriminatorValue(value = "1")
@NamedQueries(
        {
            @NamedQuery(
                    name = "Customer.findAll",
                    query = "SELECT c FROM Customer c"
            ),
            @NamedQuery(
                    name = "Customer.count",
                    query = "SELECT COUNT(c.id) FROM Customer c"
            ),
            @NamedQuery(
                    name = "Customer.getFavoritesCount",
                    query = "SELECT COUNT(*) FROM Product p JOIN p.interestedUsers c WHERE c.id=:custId"
            ),
            @NamedQuery(
                    name = "Customer.getProducts",
                    query = "SELECT p.id, p.name, p.price, p.quantity FROM Product p JOIN p.interestedUsers c WHERE c.id=:custId"
            ),
            @NamedQuery(
                    name = "Customer.getLikedProducts",
                    query = "SELECT p.id FROM Product p JOIN p.interestedUsers c WHERE c.id=:custId"
            )
        })
public class Customer extends User
{

    @Embedded
    private ContactDetails contactDetails;

    public ContactDetails getContactDetails()
    {
        return contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails)
    {
        this.contactDetails = contactDetails;
    }

}
