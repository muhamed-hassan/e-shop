package cairoshop.web.models.common;

import javax.faces.context.FacesContext;
import javax.inject.Singleton;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;

import java.util.Map;

import cairoshop.utils.Scope;
import cairoshop.pages.SharedContent;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Singleton
public class ContentChanger {

    private ExternalContext externalContext;
    
    @PostConstruct
    public void initExternalContext() {
        externalContext = FacesContext.getCurrentInstance().getExternalContext();
    }
    
    public void displayContent(String contentLocation) {
        externalContext.getSessionMap()
                        .put("content", contentLocation);
    }

    public void displayContentWithMsg(String msg) {
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.put("msg", msg);
        sessionMap.put("content", SharedContent.RESULT_CONTENT);
    }

    public void displayContentWithMsg(String msg, int status, int scope) {
        Map<String, Object> dataMap = null;
        switch (scope) {
            case Scope.REQUEST:
                dataMap = externalContext.getRequestMap();                
                break;
            case Scope.SESSION:
                dataMap = externalContext.getSessionMap();
                dataMap.put("content", SharedContent.RESULT_CONTENT);
                break;
        }
        dataMap.put("msg", msg);
        dataMap.put("result", status);
    }

    public void displayNoDataFound(String noDataFoundMsg) {
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.put("result", -2);
        sessionMap.put("msg", noDataFoundMsg);
        sessionMap.put("content", SharedContent.RESULT_CONTENT);
    }

}
