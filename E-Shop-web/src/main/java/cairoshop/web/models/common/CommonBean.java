package cairoshop.web.models.common;

import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.faces.context.FacesContext;

import java.util.Map;

import cairoshop.web.models.common.pagination.Paginator;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
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
        
    public ContentChanger getContentChanger() {
        return contentChanger;
    }
    
    protected Map<String, Object> getSessionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }
    
}
