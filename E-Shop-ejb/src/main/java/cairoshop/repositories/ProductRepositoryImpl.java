package cairoshop.repositories;

import cairoshop.dtos.ProductModel;
import cairoshop.entities.Product;
import cairoshop.helpers.msgs.RepositoryMessage;
import cairoshop.repositories.exceptions.*;
import cairoshop.repositories.interfaces.ProductRepository;
import cairoshop.repositories.specs.NativeQuerySpecs;
import java.math.BigInteger;
import java.util.*;
import javax.ejb.Stateless;
import org.apache.logging.log4j.Level;
import org.hibernate.Session;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Stateless
public class ProductRepositoryImpl
        extends BaseRepository<Product>
        implements ProductRepository {

    public ProductRepositoryImpl() {
        super(Product.class);
    }

    @Override
    public List<ProductModel> findAll(NativeQuerySpecs querySpecs)
            throws RetrievalException {
        
        final List<ProductModel> data = new ArrayList<>(0);
        try (Session session = getHibernateConfigurator().getSessionFactory().openSession()) {

            List<Object[]> tmp = session.createSQLQuery(querySpecs.build()).list();

            if (tmp != null && !tmp.isEmpty()) {

                tmp.forEach(
                        record
                        -> data.add(
                                new ProductModel(
                                        (Integer) record[0],
                                        (String) record[1],
                                        (Double) record[2],
                                        (Integer) record[3])
                        )
                );

            }

        } catch (Exception ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL,
                            getClass(),
                            ex
                    );
            throw new RetrievalException(RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL, ex);
        }

        return data;
    }

    @Override
    public int getCount(NativeQuerySpecs querySpecs)
            throws RetrievalException {
        
        Integer count = null;
        try (Session session = getHibernateConfigurator().getSessionFactory().openSession()) {

            Object tmp = session.createSQLQuery(querySpecs.build()).uniqueResult();

            if (tmp instanceof BigInteger) {
                count = ((BigInteger) tmp).intValue();
            } else {
                count = ((BigInteger) ((Object[]) tmp)[0]).intValue();
            }

        } catch (Exception ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            RepositoryMessage.AN_ERROR_OCCURED_DURING_COMPUTING_ENTITY_COUNT,
                            getClass(),
                            ex
                    );
            throw new RetrievalException(RepositoryMessage.AN_ERROR_OCCURED_DURING_COMPUTING_ENTITY_COUNT, ex);
        }

        return count;
    }

    @Override
    public byte[] getImage(int pId)
            throws RetrievalException {
        
        byte[] img = null;
        try (Session session = getHibernateConfigurator().getSessionFactory().openSession()) {

            img = (byte[]) session
                    .createQuery("SELECT p.image FROM Product p WHERE p.id=:id")
                    .setParameter("id", pId)
                    .uniqueResult();

        } catch (Exception ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "An error occured during fetching product's image",
                            getClass(),
                            ex
                    );
            throw new RetrievalException("An error occured during fetching product's image", ex);
        } 

        return img;
    }

}
