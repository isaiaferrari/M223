package ch.samt.blog.data;

import ch.samt.blog.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    List<Blog> findByAuthorIgnoreCase(String author);

    List<Blog> findTop2ByOrderByLikesDesc();

}