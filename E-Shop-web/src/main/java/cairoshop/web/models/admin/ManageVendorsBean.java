package cairoshop.web.models.admin;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import java.io.Serializable;
import java.util.List;

import cairoshop.entities.Vendor;
import cairoshop.web.models.common.navigation.AdminNavigation;
import cairoshop.services.interfaces.AdminService;
import cairoshop.utils.AdminActions;
import cairoshop.utils.AdminContent;
import cairoshop.utils.AdminMessages;
import cairoshop.utils.Messages;
import cairoshop.utils.Scope;
import cairoshop.web.models.common.CommonBean;
import cairoshop.web.models.common.pagination.PlainPaginationControls;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
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
        int status = adminService.addVendor(vendor) ? 1 : -1;

        String msg = ((status == 1)
                ? vendor.getName() + Messages.ADDED_SUCCESSFULLY
                : Messages.SOMETHING_WENT_WRONG);

        getContentChanger().displayContentWithMsg(msg, status, Scope.SESSION);
    }

    // =========================================================================
    // =======> Edit vendor
    // =========================================================================
    public void onEdit(Vendor vendorToBeEdited) {
        vendorToBeEdited.setUnderEdit(true);
    }

    public void editVendor(Vendor vendorToBeEdited) {
        vendorToBeEdited.setUnderEdit(false);
        int status = (adminService.editVendor(vendorToBeEdited) ? 1 : -1);

        String msg = ((status == 1)
                ? vendorToBeEdited.getName() + Messages.EDITED_SUCCESSFULLY
                : Messages.SOMETHING_WENT_WRONG);

        getContentChanger().displayContentWithMsg(msg, status, Scope.SESSION);
    }

    // =========================================================================
    // =======> Delete vendor
    // =========================================================================
    public void deleteVendor(Vendor vendorToBeDeleted) {
        int status = adminService.deleteVendor(vendorToBeDeleted.getId()) ? 1 : -1;

        String msg = (status == 1)
                ? vendorToBeDeleted.getName() + Messages.REMOVED_SUCCESSFULLY
                : Messages.SOMETHING_WENT_WRONG;

        getContentChanger().displayContentWithMsg(msg, status, Scope.REQUEST);

        getPaginator().setDataSize(adminService.getVendorsCount());
        vendors = adminService.getVendors(getPaginator().getCursor());
        if (vendors.isEmpty()) {
            previous();
        }

        getPaginator().setChunkSize(vendors.size());
    }

    // =========================================================================
    // =======> setters and getters
    // =========================================================================
    public List<Vendor> getVendors() {
        return vendors;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
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
        int dataSize = adminService.getVendorsCount();
        int chunkSize = getPaginator().getChunkSize();

        adjustPaginationControls(((dataSize % chunkSize) == 0) ? (dataSize - chunkSize) : (dataSize - (dataSize % chunkSize)));
    }

    @Override
    public void resetPaginator() {
        getPaginator().setDataSize(adminService.getVendorsCount());

        adjustPaginationControls(0);
    }

    private void adjustPaginationControls(int cursor) {
        vendors = adminService.getVendors(cursor);
        getPaginator().setCursor(cursor);
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
