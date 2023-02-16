package dev.sriharsha.WeBlog.service;

import dev.sriharsha.WeBlog.dto.AllCommentDto;
import dev.sriharsha.WeBlog.dto.CommentDto;
import dev.sriharsha.WeBlog.dto.PostCommentsDto;
import dev.sriharsha.WeBlog.entity.Comment;
import dev.sriharsha.WeBlog.entity.Post;
import dev.sriharsha.WeBlog.entity.User;
import dev.sriharsha.WeBlog.exception.ResourceNotFoundException;
import dev.sriharsha.WeBlog.repository.CommentRepository;
import dev.sriharsha.WeBlog.repository.PostRepository;
import dev.sriharsha.WeBlog.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CommentDto> getAllCommentsByPost(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        List<Comment> comments = post.getComments();
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setDate(new Date());
        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
        Comment comment = commentRepository.findById(commentId).
                orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId));
        comment.setContent(commentDto.getContent());
        comment.setDate(new Date());
        Comment updatedComment = commentRepository.save(comment);
        return modelMapper.map(updatedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId));
        commentRepository.delete(comment);
    }
}
