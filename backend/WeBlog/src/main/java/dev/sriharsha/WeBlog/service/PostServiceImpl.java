package dev.sriharsha.WeBlog.service;

import dev.sriharsha.WeBlog.apiresponse.PostApiResponse;
import dev.sriharsha.WeBlog.dto.PostDto;
import dev.sriharsha.WeBlog.entity.Category;
import dev.sriharsha.WeBlog.entity.Post;
import dev.sriharsha.WeBlog.entity.User;
import dev.sriharsha.WeBlog.exception.ResourceNotFoundException;
import dev.sriharsha.WeBlog.repository.CategoryRepository;
import dev.sriharsha.WeBlog.repository.PostRepository;
import dev.sriharsha.WeBlog.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PostApiResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String direction) {
        var sortDir = direction.contains("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortDir, sortBy));
        Page<Post> postPage = postRepository.findAll(pageable);
        List<Post> listOfPosts = postPage.getContent();
        List<PostDto> listOfPostDtos = listOfPosts.stream().map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return new PostApiResponse(listOfPostDtos, postPage.getNumber(), postPage.getSize(),
                postPage.getTotalElements(), postPage.getTotalPages(), postPage.isLast());
    }

    @Override
    public PostDto getPost(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        String image = post.getImage() != null ? post.getImage() : "default.jpg";
        post.setImage(image);
        post.setCreationDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post savedPost = postRepository.save(post);

        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        String image = postDto.getImage() != null ? postDto.getImage() : "default.jpg";
        System.out.println("image name : " + image);
        post.setPostTitle(postDto.getPostTitle());
        post.setContent(postDto.getContent());
        post.setImage(image);
        post.setCreationDate(new Date());
        Post updatedPost = postRepository.save(post);
        return modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        postRepository.delete(post);
    }


    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> searchedPosts = postRepository.findByPostTitleContaining(keyword);
        List<PostDto> searchedPostDtos = searchedPosts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return searchedPostDtos;
    }

    @Override
    public PostApiResponse getAllPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy, String direction) {
        var sortDir = direction.contains("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortDir, sortBy));
        Page<Post> postPage = postRepository.findByCategory(category, pageable);
        List<Post> listOfPosts = postPage.getContent();
        List<PostDto> listOfPostDtos = listOfPosts.stream().map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return new PostApiResponse(listOfPostDtos, postPage.getNumber(), postPage.getSize(),
                postPage.getTotalElements(), postPage.getTotalPages(), postPage.isLast());
    }

    @Override
    public PostApiResponse getAllPostsByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String direction) {
        var sortDir = direction.contains("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Post> postPage = postRepository.findByUser(user, pageable);
        List<Post> listOfPosts = postPage.getContent();
        List<PostDto> listOfPostDtos = listOfPosts.stream().map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return new PostApiResponse(listOfPostDtos, postPage.getNumber(), postPage.getSize(),
                postPage.getTotalElements(), postPage.getTotalPages(), postPage.isLast());
    }
}