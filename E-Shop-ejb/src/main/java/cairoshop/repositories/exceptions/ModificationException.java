package cairoshop.repositories.exceptions;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public final class ModificationException extends Exception {

    public ModificationException() {
        super(null, null);
    }

    public ModificationException(String msg) {
        super(msg, null);
    }

    public ModificationException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
}
