package com.snmi.service;

import com.snmi.dto.CommentDTO;

/**
 * Comment service functionality contract
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
public interface CommentService {

    CommentDTO createComment(CommentDTO commentDTO);
    CommentDTO getCommentById(Long commentId);
    CommentDTO updateComment(CommentDTO commentDTO);
    CommentDTO deleteComment(Long commentId);

}
