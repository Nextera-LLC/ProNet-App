package com.Nextera.ProNetService.Service;

import com.Nextera.ProNetService.Model.Post;

import java.util.List;

public interface PostService {

    // Create a new post
    Post createPost(Post post, Integer userId);

    // Get a post by its ID
    Post getPostById(Integer id);


    // Get a posts by its User ID
    List<Post> getPostsByUserId(Integer useriId);


    // Update an existing post
    Post updatePost(Post post, Integer id);

    // Delete a post by its ID
    // Return true if deleted successfully, false if post not found
    boolean deletePostById(Integer postId);
}
