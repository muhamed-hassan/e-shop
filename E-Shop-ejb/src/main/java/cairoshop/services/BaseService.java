package cairoshop.services;

import com.demo.GlobalLogger;
import javax.inject.Inject;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public abstract class BaseService {
    
    @Inject
    private GlobalLogger globalLogger;

    protected GlobalLogger getGlobalLogger() {
        return globalLogger;
    }
    
}
