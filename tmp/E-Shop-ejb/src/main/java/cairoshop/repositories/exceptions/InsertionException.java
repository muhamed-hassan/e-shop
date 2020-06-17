package cairoshop.repositories.exceptions;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public final class InsertionException extends Exception {

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
