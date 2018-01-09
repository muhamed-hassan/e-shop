package cairoshop.web.controllers.common.pagination;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface PaginationControlsForType {
    
    void next(String selected);
    
    void previous(String selected);
    
    void first(String selected);
    
    void last(String selected);
    
    void resetPaginator(Object object);
    
}
