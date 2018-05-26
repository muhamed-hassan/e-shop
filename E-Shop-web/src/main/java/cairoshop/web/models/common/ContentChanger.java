package cairoshop.web.models.common;

import cairoshop.utils.*;
import java.util.Map;
import javax.annotation.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Singleton;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@Singleton
public class ContentChanger {

    public void displayContent(String contentLocation) {
        FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put("content", contentLocation);
    }

    public void displayContentWithMsg(String msg) {
        Map<String, Object> sessionMap = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap();

        sessionMap.put("msg", msg);
        sessionMap
                .put("content", SharedContent.RESULT_CONTENT);
    }

    public void displayContentWithMsg(String msg, int status, int scope) {

        switch (scope) {
            case Scope.REQUEST:
                Map<String, Object> requestMap = FacesContext
                        .getCurrentInstance()
                        .getExternalContext()
                        .getRequestMap();
                requestMap.put("msg", msg);
                requestMap.put("result", status);
                break;
            case Scope.SESSION:
                Map<String, Object> sessionMap = FacesContext
                        .getCurrentInstance()
                        .getExternalContext()
                        .getSessionMap();
                sessionMap.put("msg", msg);
                sessionMap.put("result", status);
                sessionMap.put("content", SharedContent.RESULT_CONTENT);
                break;
        }

    }

    public void displayNoDataFound(String noDataFoundMsg) {
        Map<String, Object> sessionMap = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap();
        sessionMap.put("result", -2);
        sessionMap.put("msg", noDataFoundMsg);
        sessionMap.put("content", SharedContent.RESULT_CONTENT);
    }

}
