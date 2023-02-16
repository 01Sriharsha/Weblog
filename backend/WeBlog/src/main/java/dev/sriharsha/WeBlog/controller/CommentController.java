package dev.sriharsha.WeBlog.controller;

import dev.sriharsha.WeBlog.dto.CommentDto;
import dev.sriharsha.WeBlog.repository.CommentRepository;
import dev.sriharsha.WeBlog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<?> retrieveAllCommentsByPostId(@PathVariable Integer postId) {
        return new ResponseEntity<>(commentService.getAllCommentsByPost(postId), HttpStatus.OK);
    }

    @PostMapping("users/{userId}/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createTheComment(
            @RequestBody CommentDto commentDto,
            @PathVariable Integer userId,
            @PathVariable Integer postId
    ) {
        return new ResponseEntity<>(commentService.createComment(commentDto, postId, userId),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<?> updateTheComment(@RequestBody CommentDto commentDto, @PathVariable Integer commentId) {
        return new ResponseEntity<>(commentService.updateComment(commentDto, commentId), HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Map<String, String>> deleteTheComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
        Map<String, String> response = new HashMap<>();
        response.put("Message", "Comment Deleted Successfully!!");
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
    }
}
