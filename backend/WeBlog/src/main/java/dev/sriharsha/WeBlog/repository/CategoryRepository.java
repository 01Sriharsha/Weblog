package dev.sriharsha.WeBlog.repository;

import dev.sriharsha.WeBlog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    public List<Category> findByCategoryTitleContaining(String keyword);
}
