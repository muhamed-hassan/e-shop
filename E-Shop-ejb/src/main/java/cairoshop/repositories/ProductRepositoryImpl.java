package cairoshop.repositories;

import javax.ejb.Stateless;

import org.apache.logging.log4j.Level;
import org.hibernate.Session;

import cairoshop.entities.Product;
import cairoshop.repositories.exceptions.RetrievalException;
import cairoshop.repositories.interfaces.ProductRepository;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Stateless
public class ProductRepositoryImpl extends BaseRepository<Product> implements ProductRepository {

    public ProductRepositoryImpl() {
        super(Product.class);
    }

    @Override
    public byte[] getImage(int pId) throws RetrievalException {
        
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
