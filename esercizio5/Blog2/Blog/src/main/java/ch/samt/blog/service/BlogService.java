package ch.samt.blog.service;

import ch.samt.blog.data.BlogRepository;
import ch.samt.blog.domain.Blog;
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

    public List<Blog> getAllPosts() {
        return blogRepository.findAll();
    }

    public Blog savePost(Blog blog) {
        return blogRepository.save(blog);
    }

    public Blog getPostById(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    public List<Blog> getPostsByAuthor(String author) {
        return blogRepository.findByAuthorIgnoreCase(author);
    }

    public List<Blog> getBestPosts() {
        return blogRepository.findTop2ByOrderByLikesDesc();
    }

    public void likePost(Long id) {
        Blog blog = getPostById(id);
        blog.setLikes(blog.getLikes() + 1);

        blogRepository.save(blog);
    }
}