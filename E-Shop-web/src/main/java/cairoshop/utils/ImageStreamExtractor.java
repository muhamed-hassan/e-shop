package cairoshop.utils;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.Level;

import com.cairoshop.GlobalLogger;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Singleton
public class ImageStreamExtractor {
    
    @Inject
    private GlobalLogger globalLogger;

    public byte[] extract(InputStream imgStream) {
        ByteArrayOutputStream imgData = new ByteArrayOutputStream();
        byte[] extractedBytes = null;
        try {
            
            byte[] buffer = new byte[2048]; // 2 KB
            int bytesRead;
            while ((bytesRead = imgStream.read(buffer)) != -1) {
                imgData.write(buffer, 0, bytesRead);
            }
            extractedBytes = imgData.toByteArray();

        } catch (IOException ex) {
            globalLogger.doLogging(Level.ERROR, "Caller::extract(InputStream imgStream)", getClass(), ex);
        }

        return extractedBytes;
    }

}
