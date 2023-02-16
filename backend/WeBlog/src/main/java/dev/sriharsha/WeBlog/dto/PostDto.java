package dev.sriharsha.WeBlog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostDto {

    private Integer id;

    private String postTitle;

    private String content;

    private String image;

    private Date creationDate;

    private CategoryDto category;

    private UserDto user;

    private List<PostCommentsDto> comments;

    public PostDto(String postTitle, String content, String image) {
        this.postTitle = postTitle;
        this.content = content;
        this.image = image;
    }
}
