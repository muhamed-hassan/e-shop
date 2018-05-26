package cairoshop.repositories;

import cairoshop.dtos.ProductModel;
import cairoshop.entities.Product;
import cairoshop.repositories.interfaces.ProductRepository;
import cairoshop.repositories.specs.NativeQuerySpecs;
import java.math.BigInteger;
import java.util.*;
import javax.ejb.Stateless;
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
    public List<ProductModel> findAll(NativeQuerySpecs querySpecs) {
        Session session = getHibernateConfigurator().getSessionFactory().openSession();

        List<Object[]> tmp = session.createSQLQuery(querySpecs.build()).list();

        final List<ProductModel> data = new ArrayList<>(0);;
        if (tmp != null && !tmp.isEmpty()) {

            tmp.forEach(
                    record -> 
                    data.add(
                            new ProductModel(
                                    (Integer) record[0], 
                                    (String) record[1], 
                                    (Double) record[2], 
                                    (Integer) record[3])
                    )
            );
            
        }

        session.close();

        return data;
    }

    @Override
    public int getCount(NativeQuerySpecs querySpecs) throws Exception {
        Session session = getHibernateConfigurator().getSessionFactory().openSession();
        Integer count = null;

        try { 

            Object tmp = session.createSQLQuery(querySpecs.build()).uniqueResult();

            if (tmp instanceof BigInteger) {
                count = ((BigInteger) tmp).intValue();
            } else {
                count = ((BigInteger) ((Object[]) tmp)[0]).intValue();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        session.close();

        return count;
    }

    @Override
    public byte[] getImage(Integer pId) throws Exception {
        Session session = getHibernateConfigurator().getSessionFactory().openSession();
        byte[] img = null;

        try {

            img = (byte[]) session
                    .createQuery("SELECT p.image FROM Product p WHERE p.id=:id")
                    .setParameter("id", pId)
                    .uniqueResult();

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            session.close();
        }

        return img;
    }

}
