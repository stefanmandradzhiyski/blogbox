package com.snmi.repository;

import com.snmi.dto.NewsSearchDTO;
import com.snmi.enums.Status;
import com.snmi.model.News;
import com.snmi.model.News_;
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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * News repository impl
 * Here you can implement more complex and custom methods
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Repository
@Qualifier("NewsRepositoryImpl")
public class NewsRepositoryImpl {

    @PersistenceContext
    private final EntityManager entityManager;

    public NewsRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<News> searchNews(NewsSearchDTO newsSearchDTO, Status status, boolean display) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<News> newsQuery = criteriaBuilder.createQuery(News.class);
        Root<News> newsRoot = newsQuery.from(News.class);

        Predicate predicate = buildPredicate(newsRoot, newsSearchDTO, status, display);

        newsQuery = newsQuery.where(predicate);
        TypedQuery<News> finalQuery = entityManager.createQuery(newsQuery);

        return finalQuery.getResultList();
    }

    private Predicate buildPredicate(Root<News> newsRoot, NewsSearchDTO newsSearchDTO, Status status, boolean display) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        Predicate predicate = criteriaBuilder.conjunction();
        Expression<Boolean> displayExpr = newsRoot.get(News_.DISPLAY);
        if (display) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.isTrue(displayExpr));
        } else {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.isFalse(displayExpr));
        }

        if (status != null) {
            Expression<String> expr = newsRoot.get(News_.STATUS);
            Predicate clause = criteriaBuilder.equal(expr, status);

            predicate = criteriaBuilder.and(predicate, clause);
        }

        Long newsId = newsSearchDTO.getId();
        if (newsId != null) {
            Expression<Long> expr = newsRoot.get(News_.ID);
            Predicate clause = criteriaBuilder.equal(expr, newsId);

            predicate = criteriaBuilder.and(predicate, clause);
        }

        String name = newsSearchDTO.getName();
        if (BlogBoxStringUtil.isNotEmpty(name)) {
            Expression<String> expr = newsRoot.get(News_.NAME);
            Predicate clause = criteriaBuilder.like(
                criteriaBuilder.lower(expr),
                "%" + name.toLowerCase() + "%"
            );

            predicate = criteriaBuilder.and(predicate, clause);
        }

        String username = newsSearchDTO.getUsername();
        if (BlogBoxStringUtil.isNotEmpty(username)) {
            Join<News, User> newsUserJoin = newsRoot.join(News_.USER);
            Expression<String> expr = newsUserJoin.get(User_.USERNAME);
            Predicate clause = criteriaBuilder.like(
                criteriaBuilder.lower(expr),
                "%" + username.toLowerCase() + "%"
            );

            predicate = criteriaBuilder.and(predicate, clause);
        }

        if (newsSearchDTO.getLastWeek()) {
            Date today = Date.from(LocalDateTime.now().minusWeeks(1).atZone(ZoneId.systemDefault()).toInstant());
            Expression<Date> expr = newsRoot.get(News_.CREATED_AT);
            Predicate clause = criteriaBuilder.greaterThanOrEqualTo(expr, today);

            predicate = criteriaBuilder.and(predicate, clause);
        }

        return predicate;
    }

}
