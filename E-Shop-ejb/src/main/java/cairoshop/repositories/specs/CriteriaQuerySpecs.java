package cairoshop.repositories.specs;

import java.util.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public final class CriteriaQuerySpecs
        extends QuerySpecs {

    private final List<Criterion> CRITERIA;
    private final List<Projection> PROJECTIONS;

    public CriteriaQuerySpecs() {
        CRITERIA = new ArrayList<>(0);
        PROJECTIONS = new ArrayList<>(0);
    }

    public CriteriaQuerySpecs addCriterion(Criterion criterion) {
        CRITERIA.add(criterion);
        return this;
    }

    public CriteriaQuerySpecs addProjection(Projection projection) {
        PROJECTIONS.add(projection);
        return this;
    }

    public Criteria build(Criteria criteria) {

        CRITERIA.forEach(criterion -> criteria.add(criterion));
        PROJECTIONS.forEach(projection -> criteria.setProjection(projection));

        if (getOrderBy() != null && getOrderDirection() != null) {
            Order order = null;

            switch (getOrderDirection()) {
                case OrderDirection.ASC:
                    order = Order.asc(getOrderBy());
                    break;
                case OrderDirection.DESC:
                    order = Order.desc(getOrderBy());
                    break;
                default:
                    order = Order.asc(getOrderBy());
            }

            criteria.addOrder(order);
        }

        return criteria;
    }

}
