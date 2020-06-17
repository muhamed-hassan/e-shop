package cairoshop.repositories.exceptions;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public final class DeletionException extends Exception {
    
    public DeletionException() {
        super(null, null);
    }

    public DeletionException(String msg) {
        super(msg, null);
    }

    public DeletionException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
}