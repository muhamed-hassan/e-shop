package cairoshop.managedbeans.customer;

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
public class FindProductBean implements Serializable
{

    @EJB
    private CustomerService customerService;

    @ManagedProperty("#{paginator}")
    private Paginator paginator;

    private String keyword;
    private List<Object[]> queryResult;
    private List<Object[]> ds;
    
    // =========================================================================
    // =======> Getters and Setters
    // =========================================================================
    public String getKeyword()
    {
        return keyword;
    }

    public List<Object[]> getQueryResult()
    {
        return queryResult;
    }

    public void setQueryResult(List<Object[]> queryResult)
    {
        this.queryResult = queryResult;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    // =========================================================================
    // =======> Navigation
    // =========================================================================
    public void navigate()
    {
        Map<String, Object> sessionMap = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap();

        if (ds == null || ds.isEmpty())
        {
            sessionMap.put("result", -2);
            sessionMap.put("msg", "No products found");
            sessionMap.put("content", "/sections/result.xhtml");

            return;
        }

        sessionMap.put("content", "/customer/products-result.xhtml");
    }

    // =========================================================================
    // =======> Pagination
    // =========================================================================
    public Paginator getPaginator()
    {
        return paginator;
    }

    public void setPaginator(Paginator paginator)
    {
        this.paginator = paginator;
    }

    public void next()
    {
        queryResult.clear();
        for (int startPosition = paginator.getCursor() + 5, iterator = 0;
             iterator < 5 && startPosition < ds.size();
             startPosition++, iterator++)
        {
            queryResult.add(ds.get(startPosition));
        }

        paginator.setCursor(paginator.getCursor() + 5);
        paginator.setChunkSize(queryResult.size());
    }

    public void previous()
    {
        queryResult.clear();
        for (int startPosition = paginator.getCursor() - 5, iterator = 0;
             iterator < 5 && startPosition < ds.size();
             startPosition++, iterator++)
        {
            queryResult.add(ds.get(startPosition));
        }

        paginator.setCursor(paginator.getCursor() - 5);
        paginator.setChunkSize(queryResult.size());

    }

    public void first()
    {
        paginator.setCursor(0);

        queryResult.clear();
        for (int startPosition = paginator.getCursor(); startPosition < 5; startPosition++)
        {
            queryResult.add(ds.get(startPosition));
        }

        paginator.setChunkSize(queryResult.size());
    }

    public void last()
    {
        Integer dataSize = ds.size();
        Integer chunkSize = paginator.getChunkSize();

        if ((dataSize % chunkSize) == 0)
        {
            paginator.setCursor(dataSize - chunkSize);
        }
        else
        {
            paginator.setCursor(dataSize - (dataSize % chunkSize));
        }

        queryResult.clear();
        for (int startPosition = paginator.getCursor(), iterator = 0;
             iterator < 5 && startPosition < ds.size();
             startPosition++, iterator++)
        {
            queryResult.add(ds.get(startPosition));
        }

        paginator.setChunkSize(queryResult.size());
    }

    public void resetPaginator()
    {
        ds = customerService.findProductByName(keyword);

        paginator.setDataSize(ds.size());
        paginator.setCursor(0);

        queryResult = new ArrayList<>();

        int limit = ((ds.size() > 5) ? 5 : ds.size());

        for (int startPosition = paginator.getCursor(); startPosition < limit; startPosition++)
        {
            queryResult.add(ds.get(startPosition));
        }

        paginator.setChunkSize(queryResult.size());

        if (queryResult == null || queryResult.isEmpty())
        {
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
