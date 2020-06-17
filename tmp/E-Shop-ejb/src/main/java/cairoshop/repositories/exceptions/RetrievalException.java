package cairoshop.repositories.exceptions;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public final class RetrievalException extends Exception {

    public RetrievalException() {
        super(null, null);
    }
    
    public RetrievalException(String msg) {
        super(msg, null);
    }
    
    public RetrievalException(String msg, Throwable exception) {
        super(msg, exception);
    }
    
}
