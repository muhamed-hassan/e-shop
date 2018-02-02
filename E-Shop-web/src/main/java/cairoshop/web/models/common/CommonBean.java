package cairoshop.web.models.common;

import cairoshop.web.models.common.pagination.Paginator;
import com.cairoshop.logger.GlobalLogger;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public class CommonBean {
    
    @Inject
    private ContentChanger contentChanger;
    
    @ManagedProperty("#{paginator}")
    private Paginator paginator;

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }
    
    public GlobalLogger getLogger() {
        return GlobalLogger.getInstance();
    }
    
    public ContentChanger getContentChanger() {
        return contentChanger;
    }
}
