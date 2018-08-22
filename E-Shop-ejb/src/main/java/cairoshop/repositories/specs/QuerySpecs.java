package cairoshop.repositories.specs;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public final class QuerySpecs {

    private final List<Condition> CONDITIONS;
    private String orderBy;
    public String orderDirection;
    private Join join;

    {
        CONDITIONS = new ArrayList<>(0);
    }

    public QuerySpecs() { }

    public QuerySpecs(Join join) {
        this.join = join;
    }

    public QuerySpecs(String orderBy, String orderDirection) {
        this.orderBy = orderBy;
        this.orderDirection = orderDirection;
    }

    public QuerySpecs addPredicate(Condition condition) {
        CONDITIONS.add(condition);
        return this;
    }

    public Join getJoin() {
        return join;
    }

    public CriteriaQuery build(CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder, Root entity) {

        List<Predicate> predicates = new ArrayList<>(0);
        
        CONDITIONS.forEach(
                (Condition condition) -> {
                    
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
        );

        criteriaQuery = criteriaQuery.where(criteriaBuilder.and(
                predicates.toArray(new Predicate[0])
        ));

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
            criteriaQuery = criteriaQuery.orderBy(order);
        }

        return criteriaQuery;
    }

}
