package com.Nextera.ProNetService.Controller;

import com.Nextera.ProNetService.Model.Post;
import com.Nextera.ProNetService.Repository.PostRepository;
import com.Nextera.ProNetService.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post createdPost = postService.createPost(post);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") Integer id){
        Post post = postService.getPostById(id);
        return  ResponseEntity.ok(post);
    }
}
