package com.snmi.util;

import com.snmi.dto.CommentDTO;
import com.snmi.dto.NewsDTO;
import com.snmi.dto.ShowStatisticDTO;
import com.snmi.dto.UserDTO;
import com.snmi.enums.Status;
import com.snmi.model.Comment;
import com.snmi.model.News;
import com.snmi.model.User;
import com.snmi.model.BlogBoxBaseStatistic;
import com.snmi.enums.DetailLevel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * BlogMapper is a blog box component
 * The mapper can be used to map entity to dto or reverse
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Component
public class BlogMapper {

    public User toUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        return user;
    }

    public UserDTO toUserDTO(User user, DetailLevel detailLevel) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setUsername(user.getUsername());

        if (DetailLevel.SHORT != detailLevel) {
            userDTO.setFavouritesCount(user.getFavouritesCount());
        }

        if (DetailLevel.FULL == detailLevel) {
            userDTO.setCreatedAt(user.getCreatedAt());
            userDTO.setUpdatedAt(user.getUpdatedAt());
            userDTO.setVersion(user.getVersion());
        }

        return userDTO;
    }

    public News toNews(NewsDTO newsDTO, User user) {
        News news = new News();
        news.setName(newsDTO.getName());
        news.setShortDescription(newsDTO.getShortDescription());
        news.setMainInformation(newsDTO.getMainInformation());
        news.setViewsCount(0L);
        news.setLikesCount(0L);
        news.setDislikesCount(0L);
        news.setFavouritesCount(0L);
        news.setStatus(Status.WAITING);
        news.setUser(user);
        news.setCreatedAt(new Date());
        news.setUpdatedAt(new Date());

        return news;
    }

    public NewsDTO toNewsDTO(News news, DetailLevel detailLevel, boolean fetchComments) {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setId(news.getId());
        newsDTO.setName(news.getName());
        newsDTO.setShortDescription(news.getShortDescription());
        newsDTO.setUserId(news.getUser().getId());
        newsDTO.setUsername(news.getUser().getUsername());
        newsDTO.setViewsCount(news.getViewsCount());
        newsDTO.setLikesCount(news.getLikesCount());
        newsDTO.setStatus(news.getStatus());

        if (fetchComments) {
            newsDTO.setCommentDTOs(toCommentDTOList(news.getComments()));
        }

        if (DetailLevel.SHORT != detailLevel) {
            newsDTO.setMainInformation(news.getMainInformation());
            newsDTO.setDislikesCount(news.getDislikesCount());
            newsDTO.setFavouritesCount(news.getFavouritesCount());
            newsDTO.setCreatedAt(news.getCreatedAt());
        }

        if (DetailLevel.FULL == detailLevel) {
            newsDTO.setUpdatedAt(news.getUpdatedAt());
            newsDTO.setVersion(news.getVersion());
        }

        return newsDTO;
    }

    public Comment toComment(CommentDTO commentDTO, News news, User user) {
        Comment comment = new Comment();
        comment.setNews(news);
        comment.setUser(user);
        comment.setRating(commentDTO.getRating());
        comment.setFullComment(commentDTO.getFullComment());
        comment.setCreatedAt(new Date());
        comment.setUpdatedAt(new Date());

        return comment;
    }

    public CommentDTO toCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setNewsId(comment.getNews().getId());
        commentDTO.setNewsName(comment.getNews().getName());
        commentDTO.setUserId(comment.getUser().getId());
        commentDTO.setUsername(comment.getUser().getUsername());
        commentDTO.setRating(comment.getRating());
        commentDTO.setFullComment(comment.getFullComment());

        return commentDTO;
    }

    public ShowStatisticDTO toShowStatisticDTO(BlogBoxBaseStatistic blogBoxBaseStatistic) {
        ShowStatisticDTO showStatisticDTO = new ShowStatisticDTO();
        showStatisticDTO.setId(blogBoxBaseStatistic.getUser().getId());
        showStatisticDTO.setUsername(blogBoxBaseStatistic.getUser().getUsername());

        return showStatisticDTO;
    }

    private List<CommentDTO> toCommentDTOList(List<Comment> comments) {
        List<CommentDTO> commentDTOs = new ArrayList<>();
        comments.forEach(comment -> {
            CommentDTO commentDTO = toCommentDTO(comment);
            commentDTOs.add(commentDTO);
        });

        return commentDTOs;
    }
}
