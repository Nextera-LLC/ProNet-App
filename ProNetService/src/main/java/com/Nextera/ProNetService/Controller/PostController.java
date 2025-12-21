package com.Nextera.ProNetService.Controller;

import com.Nextera.ProNetService.Model.Post;
import com.Nextera.ProNetService.Repository.PostRepository;
import com.Nextera.ProNetService.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/{userId}/post")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @PathVariable Integer userId) {
        Post createdPost = postService.createPost(post, userId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") Integer id){
        Post post = postService.getPostById(id);
        return  ResponseEntity.ok(post);
    }

    @GetMapping("/{userId}/posts")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable("userId") Integer userId){
        List<Post> postsByUserId = postService.getPostsByUserId(userId);
        return  ResponseEntity.ok(postsByUserId);
    }
}
