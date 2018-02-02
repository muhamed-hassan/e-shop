package cairoshop.web.models.common.pagination;

import javax.faces.event.ActionEvent;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface PaginationControlsWithEvent 
        extends BasePaginationControls {
    
    void resetPaginator(ActionEvent event);
    
}
