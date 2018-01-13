package cairoshop.web.controllers.admin;

import cairoshop.web.controllers.common.CommonBean;
import cairoshop.web.controllers.common.navigation.AdminNavigation;
import cairoshop.entities.*;
import cairoshop.service.*;
import cairoshop.utils.*;
import cairoshop.web.controllers.common.pagination.*;
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
        int status = (adminService.addCategory(category) ? 1 : -1);

        String msg = ((status == 1) ? 
                category.getName() + Messages.ADDED_SUCCESSFULLY : 
                Messages.SOMETHING_WENT_WRONG);
        
        getContentChanger().displayContentWithMsg(msg, status, Scope.SESSION);
    }

    // =========================================================================
    // =======> Edit category
    // =========================================================================
    public String onEdit(Category c) {
        Integer toEditId = c.getId();
        c.setOldValue(c.getName());

        for (Category tmp : categories) {
            if (tmp.getId() == toEditId) {
                c.setCanEdit(true);
                break;
            }
        }
        
        return null; //stay on the same page
    }

    public void editCategory(Category c) {
        Integer toEditId = c.getId();

        for (Category tmp : categories) {
            if (tmp.getId() == toEditId) {
                c.setCanEdit(false); // for toggling purposes between input and output (text box)
                break;
            }
        }

        if (!c.getOldValue().equals(c.getName())) {
            int status = (adminService.editCategory(c) ? 1 : -1);

            String msg = ((status == 1) ? 
                    category.getName() + Messages.EDITED_SUCCESSFULLY : 
                    Messages.SOMETHING_WENT_WRONG);
            
            getContentChanger().displayContentWithMsg(msg, status, Scope.SESSION);
        }
    }

    // =========================================================================
    // =======> Delete categroy
    // =========================================================================
    public void deleteCategory(Category c) {
        String currentCategoryName = c.getName();
        int status = (adminService.deleteCategory(c) ? 1 : -1);

        String msg = ((status == 1) ? 
                currentCategoryName + Messages.REMOVED_SUCCESSFULLY : 
                Messages.SOMETHING_WENT_WRONG);
        
        getContentChanger().displayContentWithMsg(msg, status, Scope.REQUEST);

        getPaginator().setDataSize(adminService.getCategoriesCount());
        categories = adminService.viewCategories(getPaginator().getCursor());
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
        categories = adminService.viewCategories(getPaginator().getCursor() + 5);
        getPaginator().setCursor(getPaginator().getCursor() + 5);
        getPaginator().setChunkSize(categories.size());
    }

    @Override
    public void previous() {
        categories = adminService.viewCategories(getPaginator().getCursor() - 5);
        getPaginator().setCursor(getPaginator().getCursor() - 5);
        getPaginator().setChunkSize(categories.size());
    }

    @Override
    public void first() {
        getPaginator().setCursor(0);
        categories = adminService.viewCategories(getPaginator().getCursor());
        getPaginator().setChunkSize(categories.size());
    }

    @Override
    public void last() {
        Integer dataSize = adminService.getCategoriesCount();
        Integer chunkSize = getPaginator().getChunkSize();

        if ((dataSize % chunkSize) == 0) {
            getPaginator().setCursor(dataSize - chunkSize);
        } else {
            getPaginator().setCursor(dataSize - (dataSize % chunkSize));
        }

        categories = adminService.viewCategories(getPaginator().getCursor());
        getPaginator().setChunkSize(categories.size());
    }

    @Override
    public void resetPaginator() {
        getPaginator().setDataSize(adminService.getCategoriesCount());
        getPaginator().setCursor(0);
        categories = adminService.viewCategories(getPaginator().getCursor());
        getPaginator().setChunkSize(categories.size());
    }

    // =========================================================================
    // =======> getters and setters
    // =========================================================================
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
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
