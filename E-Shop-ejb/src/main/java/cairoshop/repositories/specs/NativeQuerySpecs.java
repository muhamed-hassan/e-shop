package cairoshop.repositories.specs;

import java.util.List;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public final class NativeQuerySpecs
        extends QuerySpecs {

    private List<TableMetadata> tables;
    private TableMetadata table;
    private String offset;
    private List<String> criteria;
    private List<String> groupBy;
    private List<String> having;
    
    public NativeQuerySpecs(List<TableMetadata> tables) {
        this.tables = tables;
    }

    public NativeQuerySpecs(TableMetadata table) {
        this.table = table;
    }

    public NativeQuerySpecs groupBy(List<String> groupBy) {
        this.groupBy = groupBy;
        return this;
    }

    public NativeQuerySpecs having(List<String> having) {
        this.having = having;
        return this;
    }

    public NativeQuerySpecs addCriteria(List<String> criteria) {
        this.criteria = criteria;
        return this;
    }

    public NativeQuerySpecs startFrom(int startPosition) {
        offset = startPosition + "";
        return this;
    }

    public String build() {

        StringBuilder nativeQuery = new StringBuilder()
                .append("SELECT ");

        if (tables != null) {
            tables.forEach((table) -> {
                List<String> fields = table.getFields();
                if (fields != null && !fields.isEmpty()) {
                    fields.forEach((field) -> {
                        nativeQuery
                                .append(table.getTableName())
                                .append(".")
                                .append(field)
                                .append(",");
                    });
                }
            });

            nativeQuery.deleteCharAt(nativeQuery.length() - 1);

            nativeQuery.append(" FROM ");
            nativeQuery.append(tables.get(0).getTableName());
            
            for (int i = 1; i < tables.size(); i++) {
                nativeQuery
                        .append(" INNER JOIN ")
                        .append(tables.get(i).getTableName())
                        .append(" ON (")
                        .append(tables.get(i - 1).getTableName()).append(".").append(tables.get(i - 1).getJoinKey())
                        .append("=")
                        .append(tables.get(i).getTableName()).append(".").append(tables.get(i).getJoinKey())
                        .append(")");
            }
            
        } else {
            
            List<String> fields = table.getFields();
            fields.forEach((field) -> {
                nativeQuery
                        .append(field)
                        .append(",");
            });
            
            nativeQuery.deleteCharAt(nativeQuery.length() - 1);
            nativeQuery.append(" FROM ").append(table.getTableName());
            
        }

        if (criteria != null && !criteria.isEmpty()) {
            nativeQuery.append(" WHERE ");
            criteria.forEach((criterion) -> {
                nativeQuery
                        .append(" ")
                        .append(criterion)
                        .append(" ");
            });
        }

        if (groupBy != null && !groupBy.isEmpty()) {
            nativeQuery.append(" GROUP BY ");
            groupBy.forEach((fieldToGroup) -> {
                nativeQuery
                        .append(" ")
                        .append(fieldToGroup)
                        .append(" ");
            });

            if (having != null && !having.isEmpty()) {
                nativeQuery.append(" HAVING ");

                having.forEach((fieldCriterion) -> {
                    nativeQuery.append(fieldCriterion).append(",");
                });
                
                nativeQuery.deleteCharAt(nativeQuery.length() - 1);
            }
        }

        if (getOrderBy() != null && getOrderDirection() != null) {
            nativeQuery
                    .append(" ORDER BY ")
                    .append(getOrderBy())
                    .append(" ")
                    .append(getOrderDirection());
        }

        if (offset != null && !offset.isEmpty()) {
            nativeQuery
                    .append(" LIMIT 5")
                    .append(" OFFSET ")
                    .append(offset);
        }

        return nativeQuery.toString();
    }
}
