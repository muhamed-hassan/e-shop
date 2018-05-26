package cairoshop.repositories.exceptions;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public class InsertionException 
        extends Exception {

    public InsertionException() {
        super(null, null);
    }

    public InsertionException(String msg) {
        super(msg, null);
    }

    public InsertionException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
}
