package cairoshop.services.helpers;

import cairoshop.repositories.specs.*;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public class CommonQuerySpecs {
    
    public static final CriteriaQuerySpecs FIND_NOT_DELETED_ITEMS_QUERY = new CriteriaQuerySpecs()
            .addPredicate(new Condition("notDeleted", true));
    
}
