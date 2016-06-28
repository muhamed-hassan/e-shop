package cairoshop.managedbeans.common;

import java.io.*;
import javax.faces.bean.*;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@SessionScoped
public class Paginator implements Serializable
{

    private Integer cursor;
    private Integer dataSize;
    private Integer chunkSize;

    public Integer getCursor()
    {
        return cursor;
    }

    public void setCursor(Integer cursor)
    {
        this.cursor = cursor;
    }

    public Integer getDataSize()
    {
        return dataSize;
    }

    public void setDataSize(Integer dataSize)
    {
        this.dataSize = dataSize;
    }

    public Integer getChunkSize()
    {
        return chunkSize;
    }

    public void setChunkSize(Integer chunkSize)
    {
        this.chunkSize = chunkSize;
    }
}
