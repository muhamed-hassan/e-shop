package cairoshop.utils;

import java.io.*;
import javax.inject.Singleton;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Singleton
public class ImageStreamExtractor {

    public byte[] extract(InputStream imgStream) {

        ByteArrayOutputStream imgData = new ByteArrayOutputStream();
        try {
            
            byte[] buffer = new byte[2048]; // 2 KB
            int bytesRead;

            while ((bytesRead = imgStream.read(buffer)) != -1) {
                imgData.write(buffer, 0, bytesRead);
            }

        } catch (IOException ex) {
            return null;
        }

        return imgData.toByteArray();
    }

}
