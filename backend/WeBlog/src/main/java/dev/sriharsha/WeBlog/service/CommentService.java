package dev.sriharsha.WeBlog.service;

import dev.sriharsha.WeBlog.dto.AllCommentDto;
import dev.sriharsha.WeBlog.dto.CommentDto;
import dev.sriharsha.WeBlog.dto.PostCommentsDto;
import dev.sriharsha.WeBlog.dto.PostDto;

import java.util.List;

public interface CommentService {

    public List<CommentDto> getAllCommentsByPost(Integer id);

    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);

    public CommentDto updateComment(CommentDto commentDto, Integer commentId);

    public void deleteComment(Integer commentId);

}
