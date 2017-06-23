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
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@SessionScoped
public class ManageCategoriesBean implements Serializable, NavigationRule {

    @EJB
    private AdminService adminService;

    @ManagedProperty("#{paginator}")
    private Paginator paginator;

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

        Map<String, Object> sessionMap = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap();
        sessionMap.put("result", status);

        String msg = ((status == 1) ? category.getName() + " added successfully." : "Something went wrong - please try again later.");
        sessionMap.put("msg", msg);

        FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put("content", "/sections/result.xhtml");
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

            Map<String, Object> sessionMap = FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap();
            sessionMap.put("result", status);

            String msg = ((status == 1) ? category.getName() + " edited successfully." : "Something went wrong - please try again later.");
            sessionMap.put("msg", msg);

            FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap()
                    .put("content", "/sections/result.xhtml");
        }
    }

    // =========================================================================
    // =======> Delete categroy
    // =========================================================================
    public void deleteCategory(Category c) {
        int status = (adminService.deleteCategory(c) ? 1 : -1);

        Map<String, Object> reqMap = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequestMap();
        reqMap.put("result", status);

        String msg = ((status == 1) ? category.getName() + " removed successfully." : "Something went wrong - please try again later.");
        reqMap.put("msg", msg);

        paginator.setDataSize(adminService.getCategoriesCount());
        categories = adminService.viewCategories(paginator.getCursor());
        if (categories.isEmpty()) {
            previous();
        }

        paginator.setChunkSize(categories.size());
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
        categories = adminService.viewCategories(paginator.getCursor() + 5);
        paginator.setCursor(paginator.getCursor() + 5);
        paginator.setChunkSize(categories.size());
    }

    public void previous() {
        categories = adminService.viewCategories(paginator.getCursor() - 5);
        paginator.setCursor(paginator.getCursor() - 5);
        paginator.setChunkSize(categories.size());
    }

    public void first() {
        paginator.setCursor(0);
        categories = adminService.viewCategories(paginator.getCursor());
        paginator.setChunkSize(categories.size());
    }

    public void last() {
        Integer dataSize = adminService.getCategoriesCount();
        Integer chunkSize = paginator.getChunkSize();

        if ((dataSize % chunkSize) == 0) {
            paginator.setCursor(dataSize - chunkSize);
        } else {
            paginator.setCursor(dataSize - (dataSize % chunkSize));
        }

        categories = adminService.viewCategories(paginator.getCursor());
        paginator.setChunkSize(categories.size());
    }

    public void resetPaginator() {
        paginator.setDataSize(adminService.getCategoriesCount());
        paginator.setCursor(0);
        categories = adminService.viewCategories(paginator.getCursor());
        paginator.setChunkSize(categories.size());
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
        String goTo = "/admin/";

        if (destination.equals("addCategory")) {
            goTo += "manage-categories/add-new-category.xhtml";
        } else {
            if (categories == null || categories.isEmpty()) {
                Map<String, Object> sessionMap = FacesContext
                        .getCurrentInstance()
                        .getExternalContext()
                        .getSessionMap();
                sessionMap.put("result", -2);
                sessionMap.put("msg", "No categories to display");
                sessionMap.put("content", "/sections/result.xhtml");

                return;
            }

            if (destination.equals("editCategory")) {
                goTo += "manage-categories/edit-category.xhtml";
            } else if (destination.equals("deleteCategory")) {
                goTo += "manage-categories/delete-category.xhtml";
            }
        }

        FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put("content", goTo);
    }

}
