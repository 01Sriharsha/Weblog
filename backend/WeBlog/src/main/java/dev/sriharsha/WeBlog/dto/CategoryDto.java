package dev.sriharsha.WeBlog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private int id;

    @NotEmpty(message = "Category title cannot be empty")
    @Size(min = 3, max = 15, message = "Category title should be least of 3 characters and " +
            "should not not exceed more than 15 characters")
    private String categoryTitle;

    @NotEmpty(message = "Category description cannot be empty")
    @Size(min = 3, message = "Category description should be least of 3 characters")
    private String categoryDescription;
}
