package cairoshop.configs;

import cairoshop.configs.utils.ConfigUtil;
import javax.annotation.*;
import javax.ejb.*;
import javax.inject.Inject;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.*;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Startup
@Singleton
public class HibernateConfigurator {

    @Inject
    private ConfigUtil configUtil;

    private SessionFactory sessionFactory;

    private StandardServiceRegistry standardServiceRegistry;

    @PostConstruct
    public void init() {
        try {

            standardServiceRegistry
                    = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml")
                    .build();

            MetadataSources sources = new MetadataSources(standardServiceRegistry);
            configUtil.getClasses("cairoshop.entities")
                    .forEach(entity -> {
                        sources.addAnnotatedClass(entity);
                    });

            sessionFactory = sources
                    .getMetadataBuilder()
                    .build()
                    .getSessionFactoryBuilder()
                    .build();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
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
