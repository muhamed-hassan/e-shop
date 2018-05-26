package cairoshop.configs.utils;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import static java.util.stream.Collectors.toList;
import javax.inject.Singleton;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Singleton
public class ConfigUtil {

    public List<Class> getClasses(String packageName)
            throws IOException {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        assert classLoader != null;

        return Files.list(
                Paths.get(new File(
                        classLoader.getResources(packageName.replace('.', '/'))
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
                        throw new RuntimeException("Failed in reading .class file of " + ex);
                    }
                })
                .collect(toList());
    }
}
