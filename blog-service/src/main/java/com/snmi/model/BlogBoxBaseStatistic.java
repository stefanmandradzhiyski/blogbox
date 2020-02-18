package com.snmi.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * BlogBoxBaseStatistic is super class
 * Contains every fields which other statistics entities need
 * Every other entity which can be accepted as statistic must extend that class
 * Once the entity extends it, entity repository can extends BlogBoxBaseStatisticRepository and inherit all his methods
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@MappedSuperclass
public class BlogBoxBaseStatistic implements Serializable {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    @NotNull
    protected News news;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @NotNull
    protected User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogBoxBaseStatistic that = (BlogBoxBaseStatistic) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(news, that.news) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, news, user);
    }

    @Override
    public String toString() {
        return "BlogBoxBaseStatistic{" +
                "id=" + id +
                ", news=" + news +
                ", user=" + user +
                '}';
    }
}
