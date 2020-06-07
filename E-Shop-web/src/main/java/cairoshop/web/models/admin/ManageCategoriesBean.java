package cairoshop.web.models.admin;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import java.io.Serializable;
import java.util.List;

import cairoshop.entities.Category;
import cairoshop.web.models.common.CommonBean;
import cairoshop.web.models.common.navigation.AdminNavigation;
import cairoshop.services.interfaces.AdminService;
import cairoshop.utils.AdminActions;
import cairoshop.utils.AdminContent;
import cairoshop.utils.AdminMessages;
import cairoshop.utils.Messages;
import cairoshop.utils.Scope;
import cairoshop.web.models.common.pagination.PlainPaginationControls;


/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@SessionScoped
public class ManageCategoriesBean
        extends CommonBean
        implements Serializable, AdminNavigation, PlainPaginationControls {

    @EJB
    private AdminService adminService;

    private Category category;
    private List<Category> categories;

    // =========================================================================
    // =======> Add category
    // =========================================================================
    public void createCategory() {
        category = new Category();
    }

    public void addCategory() {
        int status = adminService.addCategory(category) ? 1 : -1;

        String msg = (status == 1)
                ? category.getName() + Messages.ADDED_SUCCESSFULLY
                : Messages.SOMETHING_WENT_WRONG;

        getContentChanger().displayContentWithMsg(msg, status, Scope.SESSION);
    }

    // =========================================================================
    // =======> Edit category
    // =========================================================================
    public void onEdit(Category categoryToBeEdited) {
        categoryToBeEdited.setUnderEdit(true);
    }

    public void editCategory(Category categoryToBeEdited) {
        categoryToBeEdited.setUnderEdit(false);

        int status = (adminService.editCategory(categoryToBeEdited) ? 1 : -1);

        String msg = ((status == 1)
                ? categoryToBeEdited.getName() + Messages.EDITED_SUCCESSFULLY
                : Messages.SOMETHING_WENT_WRONG);

        getContentChanger().displayContentWithMsg(msg, status, Scope.SESSION);
    }

    // =========================================================================
    // =======> Delete categroy
    // =========================================================================
    public void deleteCategory(Category categoryToBeDeleted) {
        int status = (adminService.deleteCategory(categoryToBeDeleted.getId()) ? 1 : -1);

        String msg = (status == 1)
                ? categoryToBeDeleted.getName() + Messages.REMOVED_SUCCESSFULLY
                : Messages.SOMETHING_WENT_WRONG;

        getContentChanger().displayContentWithMsg(msg, status, Scope.REQUEST);

        getPaginator().setDataSize(adminService.getCategoriesCount());
        categories = adminService.getCategories(getPaginator().getCursor());
        if (categories.isEmpty()) {
            previous();
        }

        getPaginator().setChunkSize(categories.size());
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
        int dataSize = adminService.getCategoriesCount();
        int chunkSize = getPaginator().getChunkSize();
        
        adjustPaginationControls(((dataSize % chunkSize) == 0) ? (dataSize - chunkSize) : (dataSize - (dataSize % chunkSize)));
    }

    @Override
    public void resetPaginator() {
        getPaginator().setDataSize(adminService.getCategoriesCount());
        
        adjustPaginationControls(0);
    }
    
    private void adjustPaginationControls(int cursor) {
        categories = adminService.getCategories(cursor);
        getPaginator().setCursor(cursor);
        getPaginator().setChunkSize(categories.size());
    }

    // =========================================================================
    // =======> getters and setters
    // =========================================================================
    public List<Category> getCategories() {
        return categories;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // =========================================================================
    // =======> Navigation
    // =========================================================================
    @Override
    public void navigate(String destination) {
        if (destination.equals(AdminActions.ADD_CATEGORY)) {
            getContentChanger().displayContent(AdminContent.ADD_NEW_CATEGORY);
        } else {
            if (categories == null || categories.isEmpty()) {
                getContentChanger().displayNoDataFound(AdminMessages.NO_CATEGORIES_TO_DISPLAY);

                return;
            }

            switch (destination) {
                case AdminActions.EDIT_CATEGORY:
                    getContentChanger().displayContent(AdminContent.EDIT_CATEGORY);
                    break;
                case AdminActions.DELETE_CATEGORY:
                    getContentChanger().displayContent(AdminContent.DELETE_CATEGORY);
                    break;
            }
        }

    }

}
