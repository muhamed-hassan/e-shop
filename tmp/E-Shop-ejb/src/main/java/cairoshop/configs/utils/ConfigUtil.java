package cairoshop.configs.utils;

import static java.util.stream.Collectors.toList;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.logging.log4j.Level;

import com.cairoshop.GlobalLogger;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Singleton
public class ConfigUtil {

    @Inject
    private GlobalLogger globalLogger;

    public List<Class> getClasses(String packageName) throws IOException {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        assert classLoader != null;

        return Files.list(Paths.get(new File(classLoader.getResources(packageName.replace('.', '/'))
                                                .nextElement()
                                                .getFile())
                                    .getAbsolutePath()))
                .map(p -> {
                    try {
                        String currentFileName = p.toFile().getName();
                        return Class.forName(new StringBuilder()
                                                    .append(packageName).append(".")
                                                    .append(currentFileName.substring(0, currentFileName.length() - 6)).toString());
                    } catch (ClassNotFoundException ex) {
                        globalLogger.doLogging(Level.FATAL, "An error occured during reading entities", getClass(), ex);
                        throw new RuntimeException("Failed in reading .class file of " + ex);
                    }})
                .collect(toList());
    }

}
