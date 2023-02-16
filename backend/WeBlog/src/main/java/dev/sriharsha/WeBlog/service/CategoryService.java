package dev.sriharsha.WeBlog.service;

import dev.sriharsha.WeBlog.dto.CategoryDto;
import dev.sriharsha.WeBlog.entity.Category;

import java.util.List;

public interface CategoryService {

    public List<CategoryDto> getAllCategories();

    public CategoryDto getCategory(Integer categoryId);

    public CategoryDto createCategory(CategoryDto categoryDto);

    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    public void deleteCategory(Integer categoryId);

    public List<CategoryDto> searchCategory(String keyword);

}
