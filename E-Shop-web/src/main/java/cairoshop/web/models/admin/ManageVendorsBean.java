package cairoshop.web.models.admin;

import cairoshop.web.models.common.navigation.AdminNavigation;
import cairoshop.entities.*;
import cairoshop.service.interfaces.AdminService;
import cairoshop.utils.*;
import cairoshop.web.models.common.CommonBean;
import cairoshop.web.models.common.pagination.PlainPaginationControls;
import java.io.*;
import java.util.*;
import javax.ejb.*;
import javax.faces.bean.*;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@SessionScoped
public class ManageVendorsBean 
        extends CommonBean 
        implements Serializable, AdminNavigation, PlainPaginationControls {

    @EJB
    private AdminService adminService;

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

        String msg = ((status == 1) ? 
                vendor.getName() + Messages.ADDED_SUCCESSFULLY : 
                Messages.SOMETHING_WENT_WRONG);
        
        getContentChanger().displayContentWithMsg(msg, status, Scope.SESSION);
    }

    // =========================================================================
    // =======> Edit vendor
    // =========================================================================
    public void onEdit(Vendor v) {
        Integer toEditId = v.getId();
        v.setOldValue(v.getName());

        for (Vendor tmp : vendors) {
            if (tmp.getId() == toEditId) {
                v.setCanEdit(true);
                break;
            }
        }
        
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

            String msg = ((status == 1) ? 
                    v.getName() + Messages.EDITED_SUCCESSFULLY : 
                    Messages.SOMETHING_WENT_WRONG);
            
            getContentChanger().displayContentWithMsg(msg, status, Scope.SESSION);
        }
    }

    // =========================================================================
    // =======> Delete vendor
    // =========================================================================
    public void deleteVendor(Vendor v) {
        String currentVendorName = v.getName();
        int status = (adminService.deleteVendor(v) ? 1 : -1);

        String msg = ((status == 1) ? 
                currentVendorName + Messages.REMOVED_SUCCESSFULLY : 
                Messages.SOMETHING_WENT_WRONG);
        
        getContentChanger().displayContentWithMsg(msg, status, Scope.REQUEST);

        getPaginator().setDataSize(adminService.getVendorsCount());
        vendors = adminService.viewVendors(getPaginator().getCursor());
        if (vendors.isEmpty()) {
            previous();
        }

        getPaginator().setChunkSize(vendors.size());
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
    @Override
    public void next() {
        vendors = adminService.viewVendors(getPaginator().getCursor() + 5);
        getPaginator().setCursor(getPaginator().getCursor() + 5);
        getPaginator().setChunkSize(vendors.size());
    }

    @Override
    public void previous() {
        vendors = adminService.viewVendors(getPaginator().getCursor() - 5);
        getPaginator().setCursor(getPaginator().getCursor() - 5);
        getPaginator().setChunkSize(vendors.size());
    }

    @Override
    public void first() {
        getPaginator().setCursor(0);
        vendors = adminService.viewVendors(getPaginator().getCursor());
        getPaginator().setChunkSize(vendors.size());
    }

    @Override
    public void last() {
        Integer dataSize = adminService.getVendorsCount();
        Integer chunkSize = getPaginator().getChunkSize();

        if ((dataSize % chunkSize) == 0) {
            getPaginator().setCursor(dataSize - chunkSize);
        } else {
            getPaginator().setCursor(dataSize - (dataSize % chunkSize));
        }

        vendors = adminService.viewVendors(getPaginator().getCursor());
        getPaginator().setChunkSize(vendors.size());
    }

    @Override
    public void resetPaginator() {
        getPaginator().setDataSize(adminService.getVendorsCount());
        getPaginator().setCursor(0);
        vendors = adminService.viewVendors(getPaginator().getCursor());
        getPaginator().setChunkSize(vendors.size());
    }

    // =========================================================================
    // =======> Navigation
    // =========================================================================
    @Override
    public void navigate(String destination) {
        if (destination.equals(AdminActions.ADD_VENDOR)) {
            getContentChanger().displayContent(AdminContent.ADD_NEW_VENDOR);
        } else {
            if (vendors == null || vendors.isEmpty()) {
                getContentChanger().displayNoDataFound(AdminMessages.NO_VENDORS_TO_DISPLAY);

                return;
            }

            switch (destination) {
                case AdminActions.EDIT_VENDOR:
                    getContentChanger().displayContent(AdminContent.EDIT_VENDOR);
                    break;
                case AdminActions.DELETE_VENDOR:
                    getContentChanger().displayContent(AdminContent.DELETE_VENDOR);
                    break;
            }
        }

    }

}
