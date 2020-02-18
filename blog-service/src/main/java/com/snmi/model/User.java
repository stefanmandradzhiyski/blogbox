package com.snmi.model;

import com.snmi.model.base.BlogBoxBaseAuditEntity;
import com.snmi.util.BlogBoxGlobalConstant;
import com.snmi.util.BlogValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.DynamicUpdate;

/**
 * User entity
 * Represents users in the system and users table in the database
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Entity
@DynamicUpdate
@Table(name = "users")
public class User extends BlogBoxBaseAuditEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "first_name")
    @NotNull(message = "First name is required")
    @Size(min = 3, max = 20, message = "First name length must be between 3 and 20")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "Last name is required")
    @Size(min = 3, max = 25, message = "Last name length must be between 3 and 25")
    private String lastName;

    @Column(name = "username", unique = true)
    @NotNull(message = "Username is required")
    @Size(min = 3, max = 25, message = "Username length must be between 3 and 25")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "favourites_count")
    @NotNull
    private Long favouritesCount = 0L;

    @Column(name = "display")
    @NotNull(message = "Flag for soft delete")
    private boolean display = true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<News> news = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Favourite> favourites = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName="id")
    )
    private Set<Role> roles;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        BlogValidator.validateStringField(firstName, BlogBoxGlobalConstant.FIRST_NAME, 3, 20);
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        BlogValidator.validateStringField(lastName, BlogBoxGlobalConstant.LAST_NAME, 3, 25);
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        BlogValidator.validateStringField(username, BlogBoxGlobalConstant.USERNAME, 3, 25);
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getFavouritesCount() {
        return favouritesCount;
    }

    public void setFavouritesCount(Long favouritesCount) {
        this.favouritesCount = favouritesCount;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Favourite> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<Favourite> favourites) {
        this.favourites = favourites;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return display == user.display &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(favouritesCount, user.favouritesCount) &&
                Objects.equals(news, user.news) &&
                Objects.equals(comments, user.comments) &&
                Objects.equals(favourites, user.favourites) &&
                Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, username, password, favouritesCount, display, news, comments, favourites, roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", favouritesCount=" + favouritesCount +
                ", display=" + display +
                ", news=" + news +
                ", comments=" + comments +
                ", favourites=" + favourites +
                ", roles=" + roles +
                '}';
    }
}
