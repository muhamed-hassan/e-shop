package cairoshop.services;

import javax.inject.Inject;

import com.cairoshop.GlobalLogger;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public abstract class BaseService {
    
    @Inject
    private GlobalLogger globalLogger;

    protected GlobalLogger getGlobalLogger() {
        return globalLogger;
    }
    
}
