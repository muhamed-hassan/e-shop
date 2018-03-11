package cairoshop.configs;

import com.cairoshop.logger.GlobalLogger;
import java.io.*;
import java.net.URL;
import java.util.*;
import org.apache.logging.log4j.Level;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {

            Configuration configuration = new AnnotationConfiguration();

            List<Class> entities = getClasses("cairoshop.entities");
            
            entities.forEach(entity -> {
                configuration.addAnnotatedClass(entity);
            });

            sessionFactory = configuration
                    .configure()
                    .buildSessionFactory();

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
            throws ClassNotFoundException, IOException {
        
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        
        assert classLoader != null;
        
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        
        while (resources.hasMoreElements()) {
            dirs.add(new File(resources.nextElement().getFile()));
        }
        
        List<Class> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        
        return classes;
    }

    private static List<Class> findClasses(File directory, String packageName) 
            throws ClassNotFoundException {
        
        List<Class> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        
        return classes;
    }

}
