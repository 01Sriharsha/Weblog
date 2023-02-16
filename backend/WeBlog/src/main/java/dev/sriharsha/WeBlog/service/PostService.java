package dev.sriharsha.WeBlog.service;

import dev.sriharsha.WeBlog.apiresponse.PostApiResponse;
import dev.sriharsha.WeBlog.dto.PostDto;

import java.util.List;

public interface PostService {

    public PostApiResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String direction);

    public PostDto getPost(Integer postId);

    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    public PostDto updatePost(PostDto postDto, Integer postId);

    public void deletePost(Integer postId);

    public List<PostDto> searchPost(String keyword);

    public PostApiResponse getAllPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy, String direction);

    public PostApiResponse getAllPostsByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String direction);


}
