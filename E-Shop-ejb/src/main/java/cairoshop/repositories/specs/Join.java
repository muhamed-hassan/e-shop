package cairoshop.repositories.specs;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public final class Join {

    private String joinAttribute;

    public Join(String joinAttribute) {
        this.joinAttribute = joinAttribute;
    }

    public String getJoinAttribute() {
        return joinAttribute;
    }

}
