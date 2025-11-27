package com.Nextera.ProNetService.Service;

import com.Nextera.ProNetService.Model.Post;

public interface PostService {

    // Create a new post
    Post createPost(Post post);

    // Get a post by its ID
    Post getPostById(Integer id);

    // Update an existing post
    Post updatePost(Post post, Integer id);

    // Delete a post by its ID
    // Return true if deleted successfully, false if post not found
    boolean deletePostById(Integer postId);
}
