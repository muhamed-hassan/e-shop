package cairoshop.utils;

import com.demo.GlobalLogger;
import java.io.*;
import javax.inject.*;
import org.apache.logging.log4j.Level;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Singleton
public class ImageStreamExtractor {
    
    @Inject
    private GlobalLogger globalLogger;

    public byte[] extract(InputStream imgStream) {

        ByteArrayOutputStream imgData = new ByteArrayOutputStream();
        try {
            
            byte[] buffer = new byte[2048]; // 2 KB
            int bytesRead;

            while ((bytesRead = imgStream.read(buffer)) != -1) {
                imgData.write(buffer, 0, bytesRead);
            }

        } catch (IOException ex) {
            globalLogger
                    .doLogging(
                            Level.ERROR, 
                            "Caller::extract(InputStream imgStream)", 
                            getClass(), 
                            ex
                    );
            return null;
        }

        return imgData.toByteArray();
    }

}
