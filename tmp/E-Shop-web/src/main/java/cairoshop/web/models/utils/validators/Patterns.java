package cairoshop.web.models.utils.validators;

import java.util.regex.Pattern;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
* LinkedIn    : https://www.linkedin.com/in/muhamed-hassan/                *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public final class Patterns {
    
    private Patterns() {}
    
    static final Pattern ADDRESS_PATTERN = Pattern.compile("^[\\w\\.,][\\s\\w\\.,]*$");
    static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w\\.]+(@)[A-Za-z]+\\.([A-Za-z]{2,3})$");
    static final Pattern MOBILE_PATTERN = Pattern.compile("^(01)\\d{9}$");
    static final Pattern PRODUCT_NAME_PATTERN = Pattern.compile("^([A-Za-z0-9\\.])([ ]?[A-Za-z0-9\\.])*$");
    static final Pattern NAME_PATTERN = Pattern.compile("^([A-Za-z]{4,10})([ ]?([A-Za-z]{4,10}))*$");
    static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].)(?=.*[a-z]).{8,}$");
    static final Pattern PRICE_PATTERN = Pattern.compile("^[1-9][0-9]{1,3}(\\.[0-9]*)?$");
    static final Pattern QUANTITY_PATTERN = Pattern.compile("^[1-9][0-9]*$");
    static final Pattern DEFAULT_PATTERN = Pattern.compile("^[A-Za-z]{2,50}$");
    
}
