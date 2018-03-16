package cairoshop.configs;

import com.cairoshop.logger.GlobalLogger;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import static java.util.stream.Collectors.toList;
import org.apache.logging.log4j.Level;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {

            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            List<Class> entities = getClasses("cairoshop.entities");

            entities.forEach(entity -> {
                configuration.addAnnotatedClass(entity);
            });
            
            sessionFactory = configuration
                    .buildSessionFactory(
                            new ServiceRegistryBuilder()
                            .applySettings(configuration.getProperties())
                            .buildServiceRegistry()
                    );

        } catch (Exception ex) {
            GlobalLogger
                    .getInstance()
                    .doLogging(Level.FATAL, "Initial SessionFactory creation failed.", ex, HibernateUtil.class);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private static List<Class> getClasses(String packageName)
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
                        return Class.forName(packageName + '.' + currentFileName.substring(0, currentFileName.length() - 6));
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException("Failed in reading .class file of " + ex);
                    }
                })
                .collect(toList());
    }

}
