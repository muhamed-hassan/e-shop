package cairoshop.service.helpers;

import cairoshop.repositories.specs.CriteriaQuerySpecs;
import org.hibernate.criterion.Restrictions;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public class CommonQuerySpecs {
    
    public static final CriteriaQuerySpecs FIND_NOT_DELETED_ITEMS_QUERY = new CriteriaQuerySpecs()
            .addCriterion(Restrictions.eq("notDeleted", true));
    
}
