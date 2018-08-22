package cairoshop.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Entity
@DiscriminatorValue(value = "2")
public class Admin extends User {

}
