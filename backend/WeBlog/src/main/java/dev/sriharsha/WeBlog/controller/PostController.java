package dev.sriharsha.WeBlog.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.sriharsha.WeBlog.apiresponse.PostApiResponse;
import dev.sriharsha.WeBlog.constant.AppConstant;
import dev.sriharsha.WeBlog.dto.PostDto;
import dev.sriharsha.WeBlog.exception.ApiException;
import dev.sriharsha.WeBlog.service.PostService;
import dev.sriharsha.WeBlog.service.file.FileService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class PostController {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String FOLDER_PATH;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<?> createNewPost(
            @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId
    ) {
        return new ResponseEntity<>(
                postService.createPost(postDto, userId, categoryId),
                HttpStatus.CREATED
        );
    }

    @PostMapping(value = "/user/{userId}/category/{categoryId}/posts-with-image")
    public ResponseEntity<?> createNewPostWithImage(
            @RequestParam String postData,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId,
            @RequestParam("image") MultipartFile image
    ) {
        PostDto postDto = null;
        try {
            String imageName = fileService.uploadImage(FOLDER_PATH, image);
            JsonObject jsonObject = new JsonParser().parse(postData).getAsJsonObject();
            postDto = new PostDto(
                    jsonObject.get("postTitle").getAsString(),
                    jsonObject.get("content").getAsString(),
                    imageName
            );
        } catch (IllegalArgumentException | MultipartException | IOException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<PostDto>(
                postService.createPost(postDto, userId, categoryId),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<?> retrieveSinglePost(@PathVariable Integer postId) {
        return new ResponseEntity<>(postService.getPost(postId), HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostApiResponse> retrieveAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.POST_TITLE, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstant.SORT_DIR, required = false) String direction
    ) {
        return new ResponseEntity<PostApiResponse>(
                postService.getAllPosts(pageNumber, pageSize, sortBy, direction),
                HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostApiResponse> retrieveAllPostsByCategory(
            @PathVariable Integer categoryId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.POST_TITLE, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstant.SORT_DIR, required = false) String direction
    ) {
        return new ResponseEntity<PostApiResponse>(
                postService.getAllPostsByCategory(categoryId, pageNumber, pageSize, sortBy, direction),
                HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<PostApiResponse> retrieveAllPostsByUser(
            @PathVariable Integer userId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.POST_TITLE, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstant.SORT_DIR, required = false) String direction
    ) {
        return new ResponseEntity<PostApiResponse>(
                postService.getAllPostsByUser(userId, pageNumber, pageSize, sortBy, direction),
                HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updateThePost(
            @RequestBody PostDto postDto,
            @PathVariable Integer postId
    ) {
        return new ResponseEntity<PostDto>(postService.updatePost(postDto, postId), HttpStatus.CREATED);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deleteThePost(@PathVariable Integer postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>("Post Deleted Successfully...", HttpStatus.OK);
    }

    @GetMapping("/posts/search")
    public ResponseEntity<?> searchPostByTitle(
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        return new ResponseEntity<>(postService.searchPost(keyword), HttpStatus.OK);
    }

    @PostMapping(value = "/posts/{postId}/image/upload", produces = {MediaType.IMAGE_JPEG_VALUE, "application/json"})
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("imageName") MultipartFile image,
            @PathVariable Integer postId
    ) {
        try {
            String img = fileService.uploadImage(FOLDER_PATH, image);
            logger.info(img);
            PostDto postDto = postService.getPost(postId);
            postDto.setImage(img);
            System.out.println(postDto.getImage());
            return new ResponseEntity<PostDto>(postService.updatePost(postDto, postId), HttpStatus.CREATED);
        } catch (MultipartException | IOException exception) {
            throw new ApiException(exception.getMessage());
        }
    }

    @GetMapping(value = "/posts/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> downloadImage(
            @PathVariable String imageName,
            HttpServletResponse response
    ) throws IOException {
        InputStream inputStream = fileService.downloadImage(FOLDER_PATH, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream, response.getOutputStream());
        return new ResponseEntity<>(HttpStatus.FOUND);
    }
}
