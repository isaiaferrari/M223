package ch.samt.blog.controller;

import ch.samt.blog.domain.Blog;
import ch.samt.blog.service.BlogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/blog")
@Controller
public class BlogController {

    private BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public String loadBlog(Model model) {
        model.addAttribute("posts", blogService.getAllPosts());
        return "blogList";
    }

   
    @GetMapping("/post")
    public String loadPostById(Model model, @RequestParam Long postid) {
        model.addAttribute("post", blogService.getPostById(postid));

        return "blogPost";
    }

    @GetMapping("/{author}")
    public String loadPostsByAuthor(Model model, @PathVariable String author) {
        List<Blog> posts = blogService.getPostsByAuthor(author);

        model.addAttribute("posts", posts);

        return "blogList";
    }

    @GetMapping("/best")
    public String loadBestPosts(Model model) {
        model.addAttribute("posts", blogService.getBestPosts());

        return "blogList";
    }

    @GetMapping("/like")
    public String likePost(@RequestParam Long postid) {
        blogService.likePost(postid);

        return "redirect:/blog";
    }

    @GetMapping("/new")
    public String loadNewPostPage(@ModelAttribute Blog blog) {
        return "newBlog";
    }

    @PostMapping("/new")
    public String savePost(@Valid Blog blog, Errors errors) {
        if (errors.hasErrors()) {
            return "newBlog";
        }

        Blog saved = blogService.savePost(blog);
        if (saved == null) {
            throw new RuntimeException("Error saving post");
        }

        return "redirect:/blog";
    }
}