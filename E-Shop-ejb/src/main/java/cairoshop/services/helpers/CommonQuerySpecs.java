package cairoshop.services.helpers;

import cairoshop.repositories.specs.Condition;
import cairoshop.repositories.specs.ConditionConnector;
import cairoshop.repositories.specs.QuerySpecs;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public final class CommonQuerySpecs {
    
    public static final QuerySpecs FIND_NOT_DELETED_ITEMS_QUERY = new QuerySpecs()
            .addPredicate(new Condition("notDeleted", ConditionConnector.EQUAL,true));
    
}
