package com.snmi.repository;

import com.snmi.dto.UserSearchDTO;
import com.snmi.model.User;
import com.snmi.model.User_;
import com.snmi.util.BlogBoxStringUtil;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * User repository impl
 * Here you can implement more complex and custom methods
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Repository
@Qualifier("UserRepositoryImpl")
public class UserRepositoryImpl {

    @PersistenceContext
    private final EntityManager entityManager;

    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<User> searchUsers(UserSearchDTO userSearchDTO, boolean display) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> userQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = userQuery.from(User.class);

        Predicate predicate = buildPredicate(userRoot, userSearchDTO, display);

        userQuery = userQuery.where(predicate);
        TypedQuery<User> finalQuery = entityManager.createQuery(userQuery);

        return finalQuery.getResultList();
    }

    private Predicate buildPredicate(Root<User> userRoot, UserSearchDTO userSearchDTO, boolean display) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        Predicate predicate = criteriaBuilder.conjunction();

        Expression<Boolean> displayExpr = userRoot.get(User_.DISPLAY);
        if (display) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.isTrue(displayExpr));
        } else {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.isFalse(displayExpr));
        }

        Long userId = userSearchDTO.getId();
        if (userId != null) {
            Expression<Long> expr = userRoot.get(User_.ID);
            Predicate clause = criteriaBuilder.equal(expr, userId);

            predicate = criteriaBuilder.and(predicate, clause);
        }

        String firstName = userSearchDTO.getFirstName();
        if (BlogBoxStringUtil.isNotEmpty(firstName)) {
            Expression<String> expr = userRoot.get(User_.FIRST_NAME);
            Predicate clause = criteriaBuilder.like(
                criteriaBuilder.lower(expr),
                "%" + firstName.toLowerCase() + "%"
            );

            predicate = criteriaBuilder.and(predicate, clause);
        }

        String lastName = userSearchDTO.getLastName();
        if (BlogBoxStringUtil.isNotEmpty(lastName)) {
            Expression<String> expr = userRoot.get(User_.LAST_NAME);
            Predicate clause = criteriaBuilder.like(
                criteriaBuilder.lower(expr),
                "%" + lastName.toLowerCase() + "%"
            );

            predicate = criteriaBuilder.and(predicate, clause);
        }

        String username = userSearchDTO.getUsername();
        if (BlogBoxStringUtil.isNotEmpty(username)) {
            Expression<String> expr = userRoot.get(User_.USERNAME);
            Predicate clause = criteriaBuilder.like(
                criteriaBuilder.lower(expr),
                "%" + username.toLowerCase() + "%"
            );

            predicate = criteriaBuilder.and(predicate, clause);
        }

        if (userSearchDTO.getLastWeek()) {
            Date today = Date.from(LocalDateTime.now().minusWeeks(1).atZone(ZoneId.systemDefault()).toInstant());
            Expression<Date> expr = userRoot.get(User_.CREATED_AT);
            Predicate clause = criteriaBuilder.greaterThanOrEqualTo(expr, today);

            predicate = criteriaBuilder.and(predicate, clause);
        }

        return predicate;
    }

}
