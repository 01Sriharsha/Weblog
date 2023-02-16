package dev.sriharsha.WeBlog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.sriharsha.WeBlog.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class PostCommentsDto {

    private Integer id;

    private String content;

    PostCommentsDto() {
    }
}
