package com.snmi.model;

import com.snmi.enums.Rating;
import com.snmi.model.base.BlogBoxBaseAuditEntity;
import com.snmi.util.BlogBoxGlobalConstant;
import com.snmi.util.BlogValidator;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Comment entity
 * Represents news comments in the system and comments table in the database
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Entity
@DynamicUpdate
@Table(name = "comments")
public class Comment extends BlogBoxBaseAuditEntity {

    private static final long serialVersionUID = -8918119977786794115L;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "news_id")
    @NotNull(message = "Comment must be related to some news")
    private News news;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @NotNull(message = "Comment must has a user")
    private User user;

    @Column(name = "rate")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Comment must has a rating. Choose between (BAD/GOOD/VERY_GOOD/EXCELLENT)")
    private Rating rating;

    @Column(name = "comment")
    @NotNull(message = "Must have a comment")
    @Size(min = 5, max = 250)
    private String fullComment;

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

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getFullComment() {
        return fullComment;
    }

    public void setFullComment(String fullComment) {
        BlogValidator.validateStringField(fullComment, BlogBoxGlobalConstant.COMMENT, 5, 250);
        this.fullComment = fullComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Comment comment1 = (Comment) o;
        return Objects.equals(news, comment1.news) &&
            Objects.equals(user, comment1.user) &&
            rating == comment1.rating &&
            Objects.equals(fullComment, comment1.fullComment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), news, user, rating, fullComment);
    }

    @Override
    public String toString() {
        return "Comment{" +
            "news=" + news +
            ", user=" + user +
            ", rate=" + rating +
            ", comment='" + fullComment + '\'' +
            '}';
    }
}
