package cairoshop.repositories.specs;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public final class Join {

    private final String joinAttribute;

    public Join(String joinAttribute) {
        this.joinAttribute = joinAttribute;
    }

    public String getJoinAttribute() {
        return joinAttribute;
    }

}
