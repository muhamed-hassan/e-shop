package cairoshop.repositories.specs;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public abstract class QuerySpecs {
    
    private String orderBy; 
    private String orderDirection;

    protected void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    protected void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    protected String getOrderBy() {
        return orderBy;
    }

    protected String getOrderDirection() {
        return orderDirection;
    }
    
    public void setSortCriteria(String orderBy, String orderDirection) {
        this.orderBy = orderBy;
        this.orderDirection = orderDirection;
    }    
    
}
