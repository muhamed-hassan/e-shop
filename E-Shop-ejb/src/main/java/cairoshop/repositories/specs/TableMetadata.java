package cairoshop.repositories.specs;

import java.util.List;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public class TableMetadata {

    private String tableName;
    private List<String> fields;
    private String joinKey;

    public TableMetadata() { }

    public TableMetadata(String tableName, List<String> fields) {
        this.tableName = tableName;
        this.fields = fields;
    }

    public TableMetadata(String tableName, List<String> fields, String joinKey) {
        this.tableName = tableName;
        this.fields = fields;
        this.joinKey = joinKey;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public String getJoinKey() {
        return joinKey;
    }

    public void setJoinKey(String joinKey) {
        this.joinKey = joinKey;
    }

}
