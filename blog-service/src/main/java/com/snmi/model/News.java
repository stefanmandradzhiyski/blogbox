package com.snmi.model;

import com.snmi.enums.Status;
import com.snmi.model.base.BlogBoxBaseAuditEntity;
import com.snmi.util.BlogBoxGlobalConstant;
import com.snmi.util.BlogValidator;

import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.DynamicUpdate;

/**
 * News entity
 * Represents news in the system and news table in the database
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Entity
@DynamicUpdate
@Table(name = "news")
public class News extends BlogBoxBaseAuditEntity {

    private static final long serialVersionUID = 2378090183996777889L;

    @Column(name = "name")
    @NotNull(message = "News must has a name")
    @Size(min = 10, max = 50, message = "News name must be between 10 and 50")
    private String name;

    @Column(name = "short_description")
    @NotNull(message = "News must has a short description")
    @Size(min = 10, max = 150, message = "News short description must be between 10 and 150")
    private String shortDescription;

    @Column(name = "main_information")
    @NotNull(message = "News must has body text")
    @Size(min = 10, max = 5000, message = "News main information must be between 10 and 5000")
    private String mainInformation;

    @Column(name = "views_count")
    @NotNull
    private Long viewsCount;

    @OneToMany(mappedBy = "news", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Like> likes;

    @Column(name = "likes_count")
    @NotNull
    private Long likesCount;

    @OneToMany(mappedBy = "news", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Dislike> dislikes;

    @Column(name = "dislikes_count")
    @NotNull
    private Long dislikesCount;

    @OneToMany(mappedBy = "news", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Favourite> favourites;

    @Column(name = "favourites_count")
    @NotNull
    private Long favouritesCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @NotNull
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @OneToMany(mappedBy = "news", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @Column(name = "display")
    @NotNull(message = "Flag for soft delete")
    private boolean display = true;

    public News() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        BlogValidator.validateStringField(name, BlogBoxGlobalConstant.NEWS_NAME, 10, 50);
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        BlogValidator.validateStringField(shortDescription, BlogBoxGlobalConstant.NEWS_SHORT_DESCRIPTION, 10, 150);
        this.shortDescription = shortDescription;
    }

    public String getMainInformation() {
        return mainInformation;
    }

    public void setMainInformation(String mainInformation) {
        BlogValidator.validateStringField(mainInformation, BlogBoxGlobalConstant.NEWS_MAIN_INFORMATION, 10, 5000);
        this.mainInformation = mainInformation;
    }

    public Long getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(Long viewsCount) {
        this.viewsCount = viewsCount;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public Long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Long likesCount) {
        this.likesCount = likesCount;
    }

    public List<Dislike> getDislikes() {
        return dislikes;
    }

    public void setDislikes(List<Dislike> dislikes) {
        this.dislikes = dislikes;
    }

    public Long getDislikesCount() {
        return dislikesCount;
    }

    public void setDislikesCount(Long dislikesCount) {
        this.dislikesCount = dislikesCount;
    }

    public List<Favourite> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<Favourite> favourites) {
        this.favourites = favourites;
    }

    public Long getFavouritesCount() {
        return favouritesCount;
    }

    public void setFavouritesCount(Long favouritesCount) {
        this.favouritesCount = favouritesCount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        if (getComments() == null) {
            this.comments = comments;
        }
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        News news = (News) o;
        return display == news.display &&
                Objects.equals(name, news.name) &&
                Objects.equals(shortDescription, news.shortDescription) &&
                Objects.equals(mainInformation, news.mainInformation) &&
                Objects.equals(viewsCount, news.viewsCount) &&
                Objects.equals(likes, news.likes) &&
                Objects.equals(likesCount, news.likesCount) &&
                Objects.equals(dislikes, news.dislikes) &&
                Objects.equals(dislikesCount, news.dislikesCount) &&
                Objects.equals(favourites, news.favourites) &&
                Objects.equals(favouritesCount, news.favouritesCount) &&
                Objects.equals(status, news.status) &&
                Objects.equals(user, news.user) &&
                Objects.equals(comments, news.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, shortDescription, mainInformation, viewsCount, likes,
                likesCount, dislikes, dislikesCount, favourites, favouritesCount, status, user, comments, display);
    }

    @Override
    public String toString() {
        return "News{" +
                "name='" + name + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", mainInformation='" + mainInformation + '\'' +
                ", viewsCount=" + viewsCount +
                ", likes=" + likes +
                ", likesCount=" + likesCount +
                ", dislikes=" + dislikes +
                ", dislikesCount=" + dislikesCount +
                ", favourites=" + favourites +
                ", favouritesCount=" + favouritesCount +
                ", status=" + status +
                ", user=" + user +
                ", comments=" + comments +
                ", display=" + display +
                '}';
    }
}
