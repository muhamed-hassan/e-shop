package cairoshop.web.controllers.customer;

import cairoshop.web.controllers.common.navigation.CustomerNavigation;
import cairoshop.web.controllers.common.pagination.PaginationControls;
import cairoshop.web.controllers.common.CommonBean;
import cairoshop.service.*;
import cairoshop.utils.CustomerContent;
import cairoshop.utils.CustomerMessages;
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
public class FindProductBean 
        extends CommonBean 
        implements Serializable, CustomerNavigation, PaginationControls {

    @EJB
    private CustomerService customerService;

    private String keyword;
    private List<Object[]> queryResult;
    private List<Object[]> ds;
    
    // =========================================================================
    // =======> Getters and Setters
    // =========================================================================
    public String getKeyword() {
        return keyword;
    }

    public List<Object[]> getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(List<Object[]> queryResult) {
        this.queryResult = queryResult;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    // =========================================================================
    // =======> Navigation
    // =========================================================================
    @Override
    public void navigate() {
        if (ds == null || ds.isEmpty()) {
            getContentChanger().displayNoDataFound(CustomerMessages.NO_PRODUCTS_TO_DISPLAY);
            
            return;
        }

        getContentChanger().displayContent(CustomerContent.PRODUCTS_RESULT);
    }

    // =========================================================================
    // =======> Pagination
    // =========================================================================
    @Override
    public void next() {
        queryResult.clear();
        for (int startPosition = getPaginator().getCursor() + 5, iterator = 0;
                iterator < 5 && startPosition < ds.size();
                startPosition++, iterator++) {
            queryResult.add(ds.get(startPosition));
        }

        getPaginator().setCursor(getPaginator().getCursor() + 5);
        getPaginator().setChunkSize(queryResult.size());
    }

    @Override
    public void previous() {
        queryResult.clear();
        for (int startPosition = getPaginator().getCursor() - 5, iterator = 0;
                iterator < 5 && startPosition < ds.size();
                startPosition++, iterator++) {
            queryResult.add(ds.get(startPosition));
        }

        getPaginator().setCursor(getPaginator().getCursor() - 5);
        getPaginator().setChunkSize(queryResult.size());

    }

    @Override
    public void first() {
        getPaginator().setCursor(0);

        queryResult.clear();
        for (int startPosition = getPaginator().getCursor(); startPosition < 5; startPosition++) {
            queryResult.add(ds.get(startPosition));
        }

        getPaginator().setChunkSize(queryResult.size());
    }

    @Override
    public void last() {
        Integer dataSize = ds.size();
        Integer chunkSize = getPaginator().getChunkSize();

        if ((dataSize % chunkSize) == 0) {
            getPaginator().setCursor(dataSize - chunkSize);
        } else {
            getPaginator().setCursor(dataSize - (dataSize % chunkSize));
        }

        queryResult.clear();
        for (int startPosition = getPaginator().getCursor(), iterator = 0;
                iterator < 5 && startPosition < ds.size();
                startPosition++, iterator++) {
            queryResult.add(ds.get(startPosition));
        }

        getPaginator().setChunkSize(queryResult.size());
    }

    public void resetPaginator() {
        ds = customerService.findProductByName(keyword);

        getPaginator().setDataSize(ds.size());
        getPaginator().setCursor(0);

        queryResult = new ArrayList<>();

        int limit = ((ds.size() > 5) ? 5 : ds.size());

        for (int startPosition = getPaginator().getCursor(); startPosition < limit; startPosition++) {
            queryResult.add(ds.get(startPosition));
        }

        getPaginator().setChunkSize(queryResult.size());

        if (queryResult == null || queryResult.isEmpty()) {
            Map<String, Object> sessionMap = FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap();
            sessionMap.put("result", -2);
            sessionMap.put("msg", "No products to display");
            sessionMap.put("content", "/sections/result.xhtml");
        }
    }

}
