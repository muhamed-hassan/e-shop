package cairoshop.services.helpers;

import java.util.*;
import javax.inject.Singleton;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Singleton
public class ProductModelFields {
    
    private final List<String> FIELDS = new ArrayList<String>(){{
        add("ID");
        add("NAME");
        add("PRICE");
        add("QUANTITY");
    }};
    
    public List<String> getCommonFields() {
        return FIELDS;
    }
    
}
