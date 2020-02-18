package com.snmi.repository;

import com.snmi.model.Comment;
import java.util.List;

import com.snmi.util.BlogBoxGlobalConstant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Comment repository
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByNewsId(Long newsId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Comment c WHERE c.id = :commentId")
    void deleteByCommentId(@Param(BlogBoxGlobalConstant.PATH_COMMENT_ID) Long commentId);

    void deleteAllByNewsId(Long newsId);

}
