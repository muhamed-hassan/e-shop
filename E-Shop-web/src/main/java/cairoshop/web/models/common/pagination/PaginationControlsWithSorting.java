package cairoshop.web.models.common.pagination;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface PaginationControlsWithSorting 
        extends BasePaginationControls{
    
    void resetPaginator(String criteria, String direction);
    
}
