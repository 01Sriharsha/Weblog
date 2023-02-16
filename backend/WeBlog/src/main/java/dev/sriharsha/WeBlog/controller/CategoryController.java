package dev.sriharsha.WeBlog.controller;

import dev.sriharsha.WeBlog.dto.CategoryDto;
import dev.sriharsha.WeBlog.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> retrieveAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> retrieveSingleCategory(@PathVariable Integer categoryId) {
        return new ResponseEntity<>(categoryService.getCategory(categoryId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createNewCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateTheCategory(@RequestBody @Valid CategoryDto categoryDto, @PathVariable Integer categoryId) {
        return new ResponseEntity<>(categoryService.updateCategory(categoryDto, categoryId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteTheCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Category Deleted Successfully...", HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchCategory(@RequestParam("keyword") String keyword) {
        return new ResponseEntity<>(categoryService.searchCategory(keyword), HttpStatus.FOUND);
    }
}
