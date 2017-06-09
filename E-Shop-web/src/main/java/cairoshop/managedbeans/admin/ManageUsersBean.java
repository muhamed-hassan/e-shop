package cairoshop.managedbeans.admin;

import cairoshop.entities.*;
import cairoshop.managedbeans.common.*;
import cairoshop.service.*;
import java.io.*;
import java.util.*;
import javax.ejb.*;
import javax.faces.bean.*;
import javax.faces.context.*;


/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@SessionScoped
public class ManageUsersBean implements Serializable, NavigationRule {

    @EJB
    private AdminService adminService;

    @ManagedProperty("#{paginator}")
    private Paginator paginator;

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
        int status = (adminService.edit(c) ? 1 : -1);

        if (status == 1) {
            for (Customer tmp : customers) {
                if (tmp.getId() == cID) {
                    c.setActive(true);
                    break;
                }
            }
        }

        Map<String, Object> reqMap = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequestMap();
        reqMap.put("result", status);
        String msg = ((status == 1) ? c.getName() + " updated successfully." : "Something went wrong - please try again later.");
        reqMap.put("msg", msg);
    }

    public void deactivate(Customer c) {
        Integer cID = c.getId();

        c.setActive(false);
        int status = (adminService.edit(c) ? 1 : -1);

        if (status == 1) {
            for (Customer tmp : customers) {
                if (tmp.getId() == cID) {
                    c.setActive(false);
                    break;
                }
            }
        }

        Map<String, Object> reqMap = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequestMap();
        reqMap.put("result", status);
        String msg = ((status == 1) ? c.getName() + " updated successfully." : "Something went wrong - please try again later.");
        reqMap.put("msg", msg);
    }

    // =========================================================================
    // =======> Pagination
    // =========================================================================
    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public void next() {
        //customers = adminService.viewCustomers(paginator.getCursor() + 5);
        List<User> data = adminService.viewCustomers(paginator.getCursor() + 5);
        customers = new ArrayList<>(0);
        for(User u : data) {
            customers.add((Customer) u);
        }
        paginator.setCursor(paginator.getCursor() + 5);
        paginator.setChunkSize(customers.size());
    }

    public void previous() {
        //customers = adminService.viewCustomers(paginator.getCursor() - 5);
        List<User> data = adminService.viewCustomers(paginator.getCursor() - 5);
        customers = new ArrayList<>(0);
        for(User u : data) {
            customers.add((Customer) u);
        }
        paginator.setCursor(paginator.getCursor() - 5);
        paginator.setChunkSize(customers.size());
    }

    public void first() {
        paginator.setCursor(0);
//        customers = adminService.viewCustomers(paginator.getCursor());
        List<User> data = adminService.viewCustomers(paginator.getCursor());
        customers = new ArrayList<>(0);
        for(User u : data) {
            customers.add((Customer) u);
        }
        paginator.setChunkSize(customers.size());
    }

    public void last() {
        Integer dataSize = adminService.getCustomersCount();
        Integer chunkSize = paginator.getChunkSize();

        if ((dataSize % chunkSize) == 0) {
            paginator.setCursor(dataSize - chunkSize);
        } else {
            paginator.setCursor(dataSize - (dataSize % chunkSize));
        }

//        customers = adminService.viewCustomers(paginator.getCursor());
List<User> data = adminService.viewCustomers(paginator.getCursor());
        customers = new ArrayList<>(0);
        for(User u : data) {
            customers.add((Customer) u);
        }
        paginator.setChunkSize(customers.size());
    }

    public void resetPaginator() {
        paginator.setDataSize(adminService.getCustomersCount());
        paginator.setCursor(0);
//        customers = adminService.viewCustomers(paginator.getCursor());
        List<User> data = adminService.viewCustomers(paginator.getCursor());
        customers = new ArrayList<>(0);
        for(User u : data) {
            customers.add((Customer) u);
        }
        paginator.setChunkSize(customers.size());
    }

    // =========================================================================
    // =======> Navigation
    // =========================================================================
    @Override
    public void navigate(String destination) {
        if (customers == null || customers.isEmpty()) {
            Map<String, Object> sessionMap = FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap();
            sessionMap.put("result", -2);
            sessionMap.put("msg", "No customers to display");
            sessionMap.put("content", "/sections/result.xhtml");

            return;
        }

        FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put("content", "/admin/manage-users/manage-users.xhtml");
    }

}
