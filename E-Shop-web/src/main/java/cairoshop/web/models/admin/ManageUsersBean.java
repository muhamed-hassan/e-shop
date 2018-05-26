package cairoshop.web.models.admin;

import cairoshop.web.models.common.navigation.AdminNavigation;
import cairoshop.entities.*;
import cairoshop.service.interfaces.AdminService;
import cairoshop.utils.*;
import cairoshop.web.models.common.CommonBean;
import java.io.*;
import java.util.*;
import javax.ejb.*;
import javax.faces.bean.*;
import cairoshop.web.models.common.pagination.PlainPaginationControls;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@SessionScoped
public class ManageUsersBean 
        extends CommonBean 
        implements Serializable, AdminNavigation, PlainPaginationControls {

    @EJB
    private AdminService adminService;
   
    private List<Customer> customers;
       
    // =========================================================================
    // =======> getters and setters
    // =========================================================================    
    public List<Customer> getCustomers() {
        return customers;
    }

    // =========================================================================
    // =======> Change users state
    // =========================================================================    
    public void activate(Customer c) {
        Integer cID = c.getId();

        c.setActive(true);
        int status = (adminService.changeUserState(c) ? 1 : -1);

        if (status == 1) {
            for (Customer tmp : customers) {
                if (tmp.getId() == cID) {
                    c.setActive(true);
                    break;
                }
            }
        }
        
        String msg = ((status == 1) ? 
                c.getName() + Messages.EDITED_SUCCESSFULLY : 
                Messages.SOMETHING_WENT_WRONG);
        
        getContentChanger().displayContentWithMsg(msg, status, Scope.REQUEST);
    }

    public void deactivate(Customer c) {
        Integer cID = c.getId();

        c.setActive(false);
        int status = (adminService.changeUserState(c) ? 1 : -1);

        if (status == 1) {
            for (Customer tmp : customers) {
                if (tmp.getId() == cID) {
                    c.setActive(false);
                    break;
                }
            }
        }

        String msg = ((status == 1) ? 
                c.getName() + Messages.EDITED_SUCCESSFULLY : 
                Messages.SOMETHING_WENT_WRONG);
        
        getContentChanger().displayContentWithMsg(msg, status, Scope.REQUEST);
    }

    // =========================================================================
    // =======> Pagination
    // =========================================================================
    @Override
    public void next() {
        List<User> data = adminService.viewCustomers(getPaginator().getCursor() + 5);
        customers = new ArrayList<>(0);
        data.forEach((u) -> {
            customers.add((Customer) u);
        });
        getPaginator().setCursor(getPaginator().getCursor() + 5);
        getPaginator().setChunkSize(customers.size());
    }

    @Override
    public void previous() {
        List<User> data = adminService.viewCustomers(getPaginator().getCursor() - 5);
        customers = new ArrayList<>(0);
        data.forEach((u) -> {
            customers.add((Customer) u);
        });
        getPaginator().setCursor(getPaginator().getCursor() - 5);
        getPaginator().setChunkSize(customers.size());
    }

    @Override
    public void first() {
        getPaginator().setCursor(0);
        List<User> data = adminService.viewCustomers(getPaginator().getCursor());
        customers = new ArrayList<>(0);
        data.forEach((u) -> {
            customers.add((Customer) u);
        });
        getPaginator().setChunkSize(customers.size());
    }

    @Override
    public void last() {
        Integer dataSize = adminService.getCustomersCount();
        Integer chunkSize = getPaginator().getChunkSize();

        if ((dataSize % chunkSize) == 0) {
            getPaginator().setCursor(dataSize - chunkSize);
        } else {
            getPaginator().setCursor(dataSize - (dataSize % chunkSize));
        }

        List<User> data = adminService.viewCustomers(getPaginator().getCursor());
        customers = new ArrayList<>(0);
        data.forEach((u) -> {
            customers.add((Customer) u);
        });
        getPaginator().setChunkSize(customers.size());
    }

    @Override
    public void resetPaginator() {
        getPaginator().setDataSize(adminService.getCustomersCount());
        getPaginator().setCursor(0);
        List<User> data = adminService.viewCustomers(getPaginator().getCursor());
        customers = new ArrayList<>(0);
        data.forEach((u) -> {
            customers.add((Customer) u);
        });
        getPaginator().setChunkSize(customers.size());
    }

    // =========================================================================
    // =======> Navigation
    // =========================================================================
    @Override
    public void navigate(String destination) {
        if (customers == null || customers.isEmpty()) {
            getContentChanger().displayNoDataFound(AdminMessages.NO_CUSTOMERS_TO_DISPLAY);

            return;
        }
        
        getContentChanger().displayContent(AdminContent.MANAGE_USERS);
    }

}
