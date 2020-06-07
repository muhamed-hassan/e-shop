package cairoshop.web.models.common.pagination;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface PaginationControlsWithSorting extends BasePaginationControls {
    
    void resetPaginator(String criteria, String direction);
    
}
