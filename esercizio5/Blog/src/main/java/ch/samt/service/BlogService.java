package ch.samt.service;

import ch.samt.data.BlogRepository;
import ch.samt.domain.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    private BlogRepository blogRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public List<Blog> findAll() {
        return blogRepository.findAll();
    }
}
