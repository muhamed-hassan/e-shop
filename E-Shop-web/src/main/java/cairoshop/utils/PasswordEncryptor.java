package cairoshop.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.inject.Singleton;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import org.apache.logging.log4j.Level;

import com.cairoshop.GlobalLogger;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
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
