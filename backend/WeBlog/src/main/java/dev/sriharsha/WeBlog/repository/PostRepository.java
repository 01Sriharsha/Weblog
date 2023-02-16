package dev.sriharsha.WeBlog.repository;

import dev.sriharsha.WeBlog.entity.Category;
import dev.sriharsha.WeBlog.entity.Post;
import dev.sriharsha.WeBlog.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    public Page<Post> findByUser(User user, Pageable pageable);

    public Page<Post> findByCategory(Category category, Pageable pageable);

    public List<Post> findByPostTitleContaining(String keyword); //The Containing word will generate 'Like' query

    /*
    We can use the query directly instead of above doesn't work
    @Query("select p from Post where p.postTitle Like :key")
    public List<Post> findByPostTitleContaining(@Param("key") String keyword);
    */
}
