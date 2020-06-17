package cairoshop.repositories.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public final class QuerySpecs {

    private final List<Condition> conditions = new LinkedList<>();
    private final List<String> fields = new LinkedList<>();
    private String orderBy;
    private String orderDirection;
    private Join join;

    public QuerySpecs() {}

    public QuerySpecs(Join join) {
        this.join = join;
    }

    public QuerySpecs(String orderBy, String orderDirection) {
        this.orderBy = orderBy;
        this.orderDirection = orderDirection;
    }

    public QuerySpecs addPredicate(Condition condition) {
        conditions.add(condition);
        return this;
    }

    public QuerySpecs addField(String field) {
        fields.add(field);
        return this;
    }

    public List<String> getFields() {
        return Collections.unmodifiableList(fields);
    }

    public Join getJoin() {
        return join;
    }

    public CriteriaQuery build(CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder, Root entity) {
        List<Predicate> predicates = new LinkedList<>();        
        for (Condition condition : conditions) {
            StringTokenizer stringTokenizer = new StringTokenizer(condition.getField(), ".");
            Path path = entity.get(stringTokenizer.nextToken());
            while (stringTokenizer.hasMoreTokens()) {
                path = path.get(stringTokenizer.nextToken());
            }

            switch (condition.getOperator()) {
                case ConditionConnector.EQUAL:
                    predicates.add(criteriaBuilder.equal(path, condition.getValue()));
                    break;
                case ConditionConnector.LIKE:
                    predicates.add(
                            criteriaBuilder.like(
                                    path,
                                    new StringBuilder("%").append(condition.getValue()).append("%").toString()));
                    break;
            }
        }

        Selection<?>[] selections = new Selection[fields.size()];
        for (int i = 0; i < selections.length; i++) {
            selections[i] = entity.get(fields.get(i));
        }
        criteriaQuery.multiselect(selections)
                        .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        Order order = null;
        if (orderBy != null && !orderBy.trim().isEmpty() && orderDirection != null && !orderDirection.trim().isEmpty()) {
            Path path = entity.get(orderBy);
            switch (orderDirection) {
                case OrderDirection.ASC:
                    order = criteriaBuilder.asc(path);
                    break;
                case OrderDirection.DESC:
                    order = criteriaBuilder.desc(path);
                    break;
            }
            criteriaQuery.orderBy(order);
        }

        return criteriaQuery;
    }

}
