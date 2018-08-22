package cairoshop.web.models.admin;

import cairoshop.entities.Customer;
import cairoshop.web.models.common.navigation.AdminNavigation;
import cairoshop.services.interfaces.AdminService;
import cairoshop.utils.AdminContent;
import cairoshop.utils.AdminMessages;
import cairoshop.utils.Messages;
import cairoshop.utils.Scope;
import cairoshop.web.models.common.CommonBean;
import cairoshop.web.models.common.pagination.PlainPaginationControls;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
    public void activate(Customer customerToBeChanged) {
        
        customerToBeChanged.setActive(true);
        int status = adminService.changeUserState(customerToBeChanged) ? 1 : -1;

        String msg = (status == 1) ? 
                customerToBeChanged.getName() + Messages.EDITED_SUCCESSFULLY : 
                Messages.SOMETHING_WENT_WRONG;
        
        getContentChanger().displayContentWithMsg(msg, status, Scope.REQUEST);
    }

    public void deactivate(Customer customerToBeChanged) {

        customerToBeChanged.setActive(false);
        int status = adminService.changeUserState(customerToBeChanged) ? 1 : -1;
        
        String msg = (status == 1) ? 
                customerToBeChanged.getName() + Messages.EDITED_SUCCESSFULLY : 
                Messages.SOMETHING_WENT_WRONG;
        
        getContentChanger().displayContentWithMsg(msg, status, Scope.REQUEST);
    }

    // =========================================================================
    // =======> Pagination
    // =========================================================================
    @Override
    public void next() {
        adjustPaginationControls(getPaginator().getCursor() + 5);
    }

    @Override
    public void previous() {        
        adjustPaginationControls(getPaginator().getCursor() - 5);
    }

    @Override
    public void first() {
        adjustPaginationControls(0);
    }

    @Override
    public void last() {
        int dataSize = adminService.getCustomersCount();
        int chunkSize = getPaginator().getChunkSize();
        
        adjustPaginationControls(((dataSize % chunkSize) == 0) ? (dataSize - chunkSize) : (dataSize - (dataSize % chunkSize)));
    }

    @Override
    public void resetPaginator() {
        getPaginator().setDataSize(adminService.getCustomersCount());
        adjustPaginationControls(0);
    }

    private void adjustPaginationControls(int cursor) {
        customers = adminService.getCustomers(cursor)
                .stream()
                .map(user -> (Customer) user)
                .collect(Collectors.toList());
        getPaginator().setCursor(cursor);
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
