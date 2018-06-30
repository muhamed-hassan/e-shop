package cairoshop.repositories.specs;

import java.util.*;
import javax.persistence.criteria.*;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public final class CriteriaQuerySpecs
        extends QuerySpecs {

    private final List<Condition> CONDITIONS;

    public CriteriaQuerySpecs() {
        CONDITIONS = new ArrayList<>(0);
    }

    public CriteriaQuerySpecs addPredicate(Condition predicate) {
        CONDITIONS.add(predicate);
        return this;
    }

    public Predicate build(CriteriaBuilder criteriaBuilder, Root entity) {
        
        List<Predicate> predicates = new ArrayList<>();
        CONDITIONS.forEach(
                condition -> predicates.add(criteriaBuilder.equal(entity.get(condition.getField()), condition.getValue()))
        );
        
        return criteriaBuilder.and(
                    predicates.toArray(new Predicate[0])
                );
    }

}
