package cairoshop.utils;

import java.security.MessageDigest;
import java.util.*;
import javax.annotation.ManagedBean;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Singleton;

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

            encryptedPassword = Base64
                    .getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
            
        } catch (Exception ex) {
            return null;
        }

        return encryptedPassword;
    }
    
}
