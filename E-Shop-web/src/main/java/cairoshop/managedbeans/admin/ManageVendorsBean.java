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
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@SessionScoped
public class ManageVendorsBean implements Serializable, NavigationRule {

    @EJB
    private AdminService adminService;

    @ManagedProperty("#{paginator}")
    private Paginator paginator;

    private Vendor vendor;
    private List<Vendor> vendors;

    // =========================================================================
    // =======> Add vendor
    // =========================================================================
    public void createVendor() {
        vendor = new Vendor();
    }

    public void addVendor() {
        int status = (adminService.addVendor(vendor) ? 1 : -1);

        Map<String, Object> sessionMap = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap();
        sessionMap.put("result", status);

        String msg = ((status == 1) ? vendor.getName() + " added successfully." : "Something went wrong - please try again later.");
        sessionMap.put("msg", msg);

        FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put("content", "/sections/result.xhtml");
    }

    // =========================================================================
    // =======> Edit vendor
    // =========================================================================
    public String onEdit(Vendor v) {
        Integer toEditId = v.getId();
        v.setOldValue(v.getName());

        for (Vendor tmp : vendors) {
            if (tmp.getId() == toEditId) {
                v.setCanEdit(true);
                break;
            }
        }

        return null; //stay on the same page
    }

    public void editVendor(Vendor v) {
        Integer toEditId = v.getId();

        for (Vendor tmp : vendors) {
            if (tmp.getId() == toEditId) {
                v.setCanEdit(false); // for toggling purposes between input and output (text box)
                break;
            }
        }

        if (!v.getOldValue().equals(v.getName())) {
            int status = (adminService.editVendor(v) ? 1 : -1);

            Map<String, Object> sessionMap = FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap();
            sessionMap.put("result", status);

            String msg = ((status == 1) ? v.getName() + " edited successfully." : "Something went wrong - please try again later.");
            sessionMap.put("msg", msg);

            FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap()
                    .put("content", "/sections/result.xhtml");
        }
    }

    // =========================================================================
    // =======> Delete vendor
    // =========================================================================
    public void deleteVendor(Vendor v) {
        int status = (adminService.deleteVendor(v) ? 1 : -1);

        Map<String, Object> reqMap = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequestMap();
        reqMap.put("result", status);

        String msg = ((status == 1) ? v.getName() + " deleted successfully." : "Something went wrong - please try again later.");
        reqMap.put("msg", msg);

        paginator.setDataSize(adminService.getVendorsCount());
        vendors = adminService.viewVendors(paginator.getCursor());
        if (vendors.isEmpty()) {
            previous();
        }

        paginator.setChunkSize(vendors.size());
    }

    // =========================================================================
    // =======> setters and getters
    // =========================================================================
    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public List<Vendor> getVendors() {
        return vendors;
    }

    public void setVendors(List<Vendor> vendors) {
        this.vendors = vendors;
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
        vendors = adminService.viewVendors(paginator.getCursor() + 5);
        paginator.setCursor(paginator.getCursor() + 5);
        paginator.setChunkSize(vendors.size());
    }

    public void previous() {
        vendors = adminService.viewVendors(paginator.getCursor() - 5);
        paginator.setCursor(paginator.getCursor() - 5);
        paginator.setChunkSize(vendors.size());
    }

    public void first() {
        paginator.setCursor(0);
        vendors = adminService.viewVendors(paginator.getCursor());
        paginator.setChunkSize(vendors.size());
    }

    public void last() {
        Integer dataSize = adminService.getVendorsCount();
        Integer chunkSize = paginator.getChunkSize();

        if ((dataSize % chunkSize) == 0) {
            paginator.setCursor(dataSize - chunkSize);
        } else {
            paginator.setCursor(dataSize - (dataSize % chunkSize));
        }

        vendors = adminService.viewVendors(paginator.getCursor());
        paginator.setChunkSize(vendors.size());
    }

    public void resetPaginator() {
        paginator.setDataSize(adminService.getVendorsCount());
        paginator.setCursor(0);
        vendors = adminService.viewVendors(paginator.getCursor());
        paginator.setChunkSize(vendors.size());
    }

    // =========================================================================
    // =======> Navigation
    // =========================================================================
    @Override
    public void navigate(String destination) {
        String goTo = "/admin/";

        if (destination.equals("addVendor")) {
            goTo += "manage-vendors/add-new-vendor.xhtml";
        } else {
            if (vendors == null || vendors.isEmpty()) {
                Map<String, Object> sessionMap = FacesContext
                        .getCurrentInstance()
                        .getExternalContext()
                        .getSessionMap();
                sessionMap.put("result", -2);
                sessionMap.put("msg", "No vendors to display");
                sessionMap.put("content", "/sections/result.xhtml");

                return;
            }

            if (destination.equals("editVendor")) {
                goTo += "manage-vendors/edit-vendor.xhtml";
            } else if (destination.equals("deleteVendor")) {
                goTo += "manage-vendors/delete-vendor.xhtml";
            }
        }

        FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put("content", goTo);
    }

}
