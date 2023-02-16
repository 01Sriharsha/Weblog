package dev.sriharsha.WeBlog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.sriharsha.WeBlog.entity.Post;
import dev.sriharsha.WeBlog.entity.User;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Integer id;

    private String content;

    private Date date;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer postId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String postCreationDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer userId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String userUsername;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String userEmail;
}
