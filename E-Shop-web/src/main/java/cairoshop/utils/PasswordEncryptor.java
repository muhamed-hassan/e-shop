package cairoshop.utils;

import com.cairoshop.logger.GlobalLogger;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.annotation.ManagedBean;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Singleton;
import org.apache.logging.log4j.Level;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@Singleton
public class PasswordEncryptor {
    
    public String encrypt(String strToEncrypt) {

        String encryptedPassword = null;

        try {
            
            MessageDigest sha = MessageDigest.getInstance("SHA-1");

            byte[] key = strToEncrypt.getBytes("UTF-8");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);

            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            encryptedPassword = Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
            
        } catch (Exception ex) {
            GlobalLogger.getInstance().doLogging(Level.ERROR, "Failed to encrypt the password -> PasswordEncryptor::encrypt()", ex);
            return null;
        }

        return encryptedPassword;
    }
    
}
