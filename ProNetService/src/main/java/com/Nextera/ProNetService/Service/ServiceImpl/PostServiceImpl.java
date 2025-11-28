package com.Nextera.ProNetService.Service.ServiceImpl;

import com.Nextera.ProNetService.Model.Post;
import com.Nextera.ProNetService.Repository.PostRepository;
import com.Nextera.ProNetService.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.Date;

public class PostServiceImpl implements PostService {
 @Autowired
 private PostRepository postRepository;
    @Override
    public Post createPost(Post post) {
        if(post.getCaption() ==null || post.getCaption().trim().isEmpty()){
            throw new  IllegalArgumentException(" Post cannot be empty");
        }
       if(post.getUser() == null){
           throw new IllegalArgumentException("User cannot be null");
       }
       if(post.getCaption().length()>500){
           throw new IllegalArgumentException("Post caption cannot exceed more than 500 character");
       }
        // Set creation and update timestamps using Date
        Date now = new Date();
        post.setCreatedDate(now);
        post.setUpdatedDate(now);

        return postRepository.save(post);
    }


    @Override
    public Post getPostById(Integer id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));
    }

    @Override
    public Post updatePost(Post post, Integer id) {
        return null;
    }

    @Override
    public boolean deletePostById(Integer postId) {
        return false;
    }
}
