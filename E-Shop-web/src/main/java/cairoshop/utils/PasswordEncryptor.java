package cairoshop.utils;

import com.demo.GlobalLogger;
import java.security.MessageDigest;
import java.util.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.*;
import org.apache.logging.log4j.Level;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Singleton
public class PasswordEncryptor {
    
    @Inject
    private GlobalLogger globalLogger;
    
    public String encrypt(String strToEncrypt) {

        String encryptedPassword = null;

        try {
            
            MessageDigest sha = MessageDigest.getInstance("SHA-1");

            byte[] key = strToEncrypt.getBytes("UTF-8");
            key = Arrays.copyOf(sha.digest(key), 16);

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));

            encryptedPassword = Base64
                    .getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
            
        } catch (Exception ex) {
            globalLogger
                    .doLogging(
                            Level.ERROR, 
                            "Caller::encrypt(String strToEncrypt)", 
                            getClass(), 
                            ex
                    );
            return null;
        }

        return encryptedPassword;
    }
    
}
