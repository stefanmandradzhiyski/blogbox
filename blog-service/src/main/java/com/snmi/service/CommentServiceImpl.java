package com.snmi.service;

import com.snmi.dto.CommentDTO;
import com.snmi.enums.Status;
import com.snmi.exception.BlogBoxNotFoundException;
import com.snmi.model.Comment;
import com.snmi.model.News;
import com.snmi.model.User;
import com.snmi.repository.CommentRepository;
import com.snmi.repository.NewsRepository;
import com.snmi.repository.UserRepository;
import com.snmi.util.BlogBoxGlobalConstant;
import com.snmi.util.BlogMapper;
import com.snmi.util.BlogBoxSecurityUtil;
import com.snmi.util.BlogValidator;
import java.util.Date;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Comment service implementation
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final BlogMapper blogMapper;

    public CommentServiceImpl(
        CommentRepository commentRepository,
        NewsRepository newsRepository,
        UserRepository userRepository,
        BlogMapper blogMapper
    ) {
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
        this.blogMapper = blogMapper;
    }

    /**
     * Create new news comment
     * @param commentDTO take the comment data transfer object
     * @return the created comment
     */
    @Override
    public CommentDTO createComment(CommentDTO commentDTO) {
        News news = findActiveNewsById(commentDTO.getNewsId());
        User user = findActiveUserByHisUsername();
        Comment comment = blogMapper.toComment(commentDTO, news, user);

        Comment savedComment = commentRepository.save(comment);

        return getCommentById(savedComment.getId());
    }

    /**
     * Get specific comment by id
     * @param commentId take the comment id
     * @return the found comment
     */
    @Override
    public CommentDTO getCommentById(Long commentId) {
        return blogMapper.toCommentDTO(findCommentById(commentId));
    }

    /**
     * Update specific comment
     * User can update only his comment
     * @param commentDTO take the comment data transfer object
     * @return the updated comment
     */
    @Transactional
    @Override
    public CommentDTO updateComment(CommentDTO commentDTO) {
        Comment existingComment = findCommentById(commentDTO.getId());
        BlogBoxSecurityUtil.controlYourData(existingComment.getUser().getUsername());
        Comment updatedComment = updateComment(existingComment, commentDTO);

        commentRepository.save(updatedComment);

        return getCommentById(updatedComment.getId());
    }

    /**
     * Delete specific comment
     * User can delete only his comment
     * @param commentId take the desired comment id
     * @return the comment before the delete
     */
    @Override
    public CommentDTO deleteComment(Long commentId) {
        Comment existingComment = findCommentById(commentId);
        BlogBoxSecurityUtil.controlYourData(existingComment.getUser().getUsername());

        commentRepository.deleteByCommentId(existingComment.getId());

        return blogMapper.toCommentDTO(existingComment);
    }

    private User findActiveUserByHisUsername() {
        String username = BlogBoxSecurityUtil.getLoggedInUserUsername();

        return userRepository.findByUsernameAndDisplay(username, true)
                .orElseThrow(() -> new BlogBoxNotFoundException(User.class, BlogBoxGlobalConstant.USERNAME, username));
    }

    private News findActiveNewsById(Long newsId) {
        BlogValidator.validateId(newsId, News.class);

        return newsRepository.findByIdAndDisplayAndStatus(newsId, true, Status.APPROVED)
            .orElseThrow(() -> new BlogBoxNotFoundException(News.class, newsId));
    }

    private Comment findCommentById(Long commentId) {
        BlogValidator.validateId(commentId, Comment.class);

        return commentRepository.findById(commentId)
            .orElseThrow(() -> new BlogBoxNotFoundException(Comment.class, commentId));
    }

    private Comment updateComment(Comment existingComment, CommentDTO commentDTO) {
        existingComment.setRating(commentDTO.getRating());
        existingComment.setFullComment(commentDTO.getFullComment());
        existingComment.setUpdatedAt(new Date());

        return existingComment;
    }
}
