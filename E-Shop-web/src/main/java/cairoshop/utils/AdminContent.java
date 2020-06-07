package cairoshop.utils;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface AdminContent extends SharedContent {
   
    String ADD_NEW_CATEGORY = "/admin/manage-categories/add-new-category.xhtml";
    String EDIT_CATEGORY = "/admin/manage-categories/edit-category.xhtml";
    String DELETE_CATEGORY = "/admin/manage-categories/delete-category.xhtml";
    
    String ADD_NEW_PRODUCT = "/admin/manage-products/add-new-product.xhtml";
    String EDIT_PRODUCT_MASTER = "/admin/manage-products/edit-product-master.xhtml";
    String EDIT_PRODUCT_DETAILS = "/admin/manage-products/edit-product-pg.xhtml";
    String DELETE_PRODUCT = "/admin/manage-products/delete-product.xhtml";
    
    String ADD_NEW_VENDOR = "/admin/manage-vendors/add-new-vendor.xhtml";
    String EDIT_VENDOR = "/admin/manage-vendors/edit-vendor.xhtml";
    String DELETE_VENDOR = "/admin/manage-vendors/delete-vendor.xhtml";
    
    String MANAGE_USERS = "/admin/manage-users/manage-users.xhtml";
    
}
