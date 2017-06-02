package cairoshop.daos;

import cairoshop.configs.HibernateUtil;
import cairoshop.entities.*;
import cairoshop.helpers.*;
import com.cairoshop.logger.GlobalLogger;
import java.util.*;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import org.hibernate.*;
import org.apache.logging.log4j.Level;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
public class ProductDAOImpl implements ProductDAO {

    private GlobalLogger logger;

    @PostConstruct
    public void init() {
        logger = GlobalLogger.getInstance();
    }

    @Override
    public boolean insert(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.getTransaction().begin();

            session.save(product);

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            logger.doLogging(Level.ERROR, "Product insertion failed" + " | " + ProductDAOImpl.class.getName() + "::insert(product)", ex);
            return false;
        } finally {
            session.close();
        }

        return true;
    }

    @Override
    public boolean update(Product p) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        int affectedRows = -1;

        try {
            session.getTransaction().begin();

            affectedRows = session.getNamedQuery("Product.update")
                    .setParameter("catId", p.getCategory().getId())
                    .setParameter("desc", p.getDescription())
                    .setParameter("pName", p.getName())
                    .setParameter("price", p.getPrice())
                    .setParameter("quantity", p.getQuantity())
                    .setParameter("vId", p.getVendor().getId())
                    .setParameter("pId", p.getId())
                    .executeUpdate();

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            logger.doLogging(Level.ERROR, "Product update failed" + " | " + ProductDAOImpl.class.getName() + "::update(product)", ex);
            return false;
        } finally {
            session.close();
        }

        return (affectedRows == 1);
    }

    @Override
    public boolean delete(Integer productID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        int affectedRows = -1;

        try {
            session.getTransaction().begin();

            affectedRows = session.createQuery("UPDATE Product p SET p.notDeleted=:flag WHERE p.id=:pID")
                    .setParameter("flag", false)
                    .setParameter("pID", productID)
                    .executeUpdate();

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            logger.doLogging(Level.ERROR, "Product delete failed" + " | " + ProductDAOImpl.class.getName() + "::delete(productID)", ex);
            return false;
        } finally {
            session.close();
        }

        return (affectedRows == 1);
    }

    @Override
    public Product get(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Product product = new Product();
        Object[] tmp = null;
        Integer imgFlag = null;

        try {
            tmp = (Object[]) session.getNamedQuery("Product.loadInstance").setParameter("pId", id).uniqueResult();

            imgFlag = (Integer) session.getNamedQuery("Product.isExistImg").setParameter("pId", id).uniqueResult();

            product.setId((Integer) tmp[0]);
            product.setName((String) tmp[1]);
            product.setPrice((Double) tmp[2]);
            product.setQuantity((Integer) tmp[3]);
            product.setDescription((String) tmp[4]);
            product.setVendor((Vendor) tmp[5]);
            product.setCategory((Category) tmp[6]);
            product.setImgExist(imgFlag != null);
        } catch (Exception ex) {
            logger.doLogging(Level.ERROR, "Product retreival failed" + " | " + ProductDAOImpl.class.getName() + "::get(id)", ex);
            return null;
        } finally {
            session.close();
        }

        return product;
    }

    @Override
    public List<Object[]> getAll(Object object, Integer startPosition) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> products = null;

        try {
            if (object instanceof Vendor) {
                products = (List<Object[]>) session
                        .getNamedQuery("Vendor.getProducts")
                        .setParameter("venId", ((Vendor) object).getId())
                        .setParameter("flag", true)
                        .setMaxResults(5)
                        .setFirstResult(startPosition)
                        .list();
            } else if (object instanceof Product) {
                products = (List<Object[]>) session
                        .getNamedQuery("Product.getProducts")
                        .setParameter("catId", ((Category) object).getId())
                        .setParameter("flag", true)
                        .setMaxResults(5)
                        .setFirstResult(startPosition)
                        .list();

            } else if (object instanceof Customer) {
                products = (List<Object[]>) session
                        .getNamedQuery("Customer.getProducts")
                        .setParameter("custId", ((Customer) object).getId())
                        .setMaxResults(5)
                        .setFirstResult(startPosition)
                        .list();
            }

        } catch (Exception ex) {
            logger.doLogging(Level.ERROR, "Product retreival failed" + " | " + ProductDAOImpl.class.getName() + "::getAll(object, pos)", ex);
            return null;
        } finally {
            session.close();
        }

        return products;
    }

    @Override
    public List<Object[]> getAll(SortCriteria sortCriteria, SortDirection sortDirection, Integer startPosition) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> products = new ArrayList<>();
        String sortCriteriaStr = (sortCriteria == SortCriteria.NAME) ? "Name" : "Price";
        String sortDirectionStr = (sortDirection == SortDirection.ASC) ? "ASC" : "DESC";

        try {
            products = (List<Object[]>) session
                    .getNamedQuery("Product.sortBy" + sortCriteriaStr + sortDirectionStr)
                    .setParameter("flag", true)
                    .setMaxResults(5)
                    .setFirstResult(startPosition)
                    .list();
        } catch (Exception ex) {
            logger.doLogging(Level.ERROR, "Product retreival failed" + " | " + ProductDAOImpl.class.getName() + "::getAll(sortCriteria, sortDirection, pos)", ex);
            return null;
        } finally {
            session.close();
        }

        return products;
    }

    @Override
    public List<Object[]> getAll(String pName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> products = new ArrayList<>();
        List<Product> tmp = null;

        try {
            tmp = (List<Product>) session
                    .getNamedQuery("Product.findByName")
                    .setParameter("pName", "%" + pName + "%")
                    .list();

            for (Product p : tmp) {
                Object[] t = new Object[4];
                t[0] = p.getId();
                t[1] = p.getName();
                t[2] = p.getPrice();
                t[3] = p.getQuantity();
                products.add(t);
            }
        } catch (Exception ex) {
            logger.doLogging(Level.ERROR, "Product retreival failed" + " | " + ProductDAOImpl.class.getName() + "::getAll(pName)", ex);
            return null;
        } finally {
            session.close();
        }

        return products;
    }

    @Override
    public boolean update(Integer pID, Integer cID) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            // we load product first cause it is the owner of the relationship
            Product p = (Product) session.get(Product.class, pID);
            Customer c = (Customer) session.get(Customer.class, cID);

            p.getInterestedUsers().add(c);

            session.merge(c);

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            logger.doLogging(Level.ERROR, "Product update failed" + " | " + ProductDAOImpl.class.getName() + "::update(pID, cID)", ex);
            return false;
        } finally {
            session.close();
        }

        return true;
    }

    @Override
    public List<Object[]> getAll(Integer startPosition) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> products = null;

        try {
            products = (List<Object[]>) session
                    .getNamedQuery("Product.findAll")
                    .setFirstResult(startPosition)
                    .setMaxResults(5)
                    .setParameter("flag", true)
                    .list();
        } catch (Exception ex) {
            logger.doLogging(Level.ERROR, "Product retreival failed" + " | " + ProductDAOImpl.class.getName() + "::getAll(startPosition)", ex);
            return null;
        } finally {
            session.close();
        }

        return products;
    }

    @Override
    public Integer getCount() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Long count = 0L;

        try {
            count = (Long) session.getNamedQuery("Product.count").setParameter("flag", true).uniqueResult();
        } catch (Exception ex) {
            logger.doLogging(Level.ERROR, "Product getCount failed" + " | " + ProductDAOImpl.class.getName() + "::getCount()", ex);
            return -1;
        } finally {
            session.close();
        }

        return (Integer) count.intValue();
    }

    @Override
    public byte[] getImage(Integer pID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        byte[] img = null;

        try {
            Query q = session.createQuery("SELECT p.image FROM Product p WHERE p.id=:id");
            q.setParameter("id", pID);
            img = (byte[]) q.uniqueResult();
        } catch (Exception ex) {
            logger.doLogging(Level.ERROR, "Product getImage failed" + " | " + ProductDAOImpl.class.getName() + "::getImage(pID)", ex);
            return null;
        } finally {
            session.close();
        }

        return img;
    }

    @Override
    public boolean update(byte[] imgStream, Integer pID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        int rowsAffected = -1;

        try {
            session.getTransaction().begin();

            rowsAffected = session.getNamedQuery("Product.updateImg")
                    .setParameter("img", imgStream)
                    .setParameter("pId", pID)
                    .executeUpdate();

            session.getTransaction().commit();
        } catch (Exception ex) {
            logger.doLogging(Level.ERROR, "Product update failed" + " | " + ProductDAOImpl.class.getName() + "::update(imgStream, pID)", ex);
            return false;
        } finally {
            session.close();
        }

        return (rowsAffected == 1);
    }

    @Override
    public Integer getCount(Object object) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Long count = 0L;

        Integer id = -1;
        String param = "";
        String namedQuery = "";

        if (object instanceof Vendor) {
            id = ((Vendor) object).getId();
            param = "vId";
            namedQuery = "Vendor.countProducts";
        } else if (object instanceof Product) {
            id = ((Product) object).getId();
            param = "cId";
            namedQuery = "Product.countProducts";
        }

        try {
            count = (Long) session
                    .getNamedQuery(namedQuery)
                    .setParameter(param, id)
                    .setParameter("flag", true)
                    .uniqueResult();
        } catch (Exception ex) {
            logger.doLogging(Level.ERROR, "Product getCount failed" + " | " + ProductDAOImpl.class.getName() + "::getCount(object)", ex);
            return -1;
        } finally {
            session.close();
        }

        return (Integer) count.intValue();
    }

    @Override
    public Integer getFavoriteCount(Integer custId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Long count = 0L;

        try {
            count = (Long) session
                    .getNamedQuery("Customer.getFavoritesCount")
                    .setParameter("custId", custId)
                    .uniqueResult();
        } catch (Exception ex) {
            logger.doLogging(Level.ERROR, "Product getFavoriteCount failed" + " | " + ProductDAOImpl.class.getName() + "::getFavoriteCount(custId)", ex);
            return -1;
        } finally {
            session.close();
        }

        return (Integer) count.intValue();
    }

    @Override
    public List<Integer> getLikedProducts(Integer custId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Integer> pIds;

        try {
            pIds = (List<Integer>) session
                    .getNamedQuery("Customer.getLikedProducts")
                    .setParameter("custId", custId)
                    .list();
        } catch (Exception ex) {
            logger.doLogging(Level.ERROR, "Product getLikedProducts failed" + " | " + ProductDAOImpl.class.getName() + "::getLikedProducts(custId)", ex);
            return null;
        } finally {
            session.close();
        }

        return pIds;
    }

}
