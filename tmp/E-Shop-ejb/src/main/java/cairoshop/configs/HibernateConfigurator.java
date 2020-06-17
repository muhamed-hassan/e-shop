package cairoshop.configs;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.annotation.Resource;

import org.apache.logging.log4j.Level;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.cairoshop.GlobalLogger;

import cairoshop.configs.utils.ConfigUtil;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Startup
@Singleton
public class HibernateConfigurator {

    @Inject
    private ConfigUtil configUtil;

    private SessionFactory sessionFactory;

    private StandardServiceRegistry standardServiceRegistry;

    @Inject
    private GlobalLogger globalLogger;

    @Resource(name = "java:global/jdbc/shopDS")
    private DataSource dataSource;

    @PostConstruct
    public void init() {
        try {
            standardServiceRegistry = new StandardServiceRegistryBuilder()
                                            .configure("hibernate.cfg.xml")
                                            .build();

            MetadataSources sources = new MetadataSources(standardServiceRegistry);            
            configUtil.getClasses("cairoshop.entities")
                        .forEach(entity -> sources.addAnnotatedClass(entity));

            sessionFactory = sources.getMetadataBuilder()
                                        .build()
                                        .getSessionFactoryBuilder()
                                        .build();

            Flyway flyway = new Flyway();
            flyway.setSchemas("cairoshop");
            flyway.setLocations("db");
            flyway.setDataSource(dataSource);
            flyway.migrate();
        } catch (Exception ex) {
            globalLogger.doLogging(Level.FATAL, "An error occured during session factory initialization", getClass(), ex);
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @PreDestroy
    public void releaseHibernateResources() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }

        if (standardServiceRegistry != null) {
            StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
        }
    }

}
