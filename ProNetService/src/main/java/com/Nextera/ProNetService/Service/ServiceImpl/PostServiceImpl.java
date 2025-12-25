package com.Nextera.ProNetService.Service.ServiceImpl;

import com.Nextera.ProNetService.Model.Post;
import com.Nextera.ProNetService.Model.User;
import com.Nextera.ProNetService.Repository.PostRepository;
import com.Nextera.ProNetService.Service.PostService;
import com.Nextera.ProNetService.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
 @Autowired
 private PostRepository postRepository;
 @Autowired
 private UserService userService;

    @Override
    public Post createPost(Post post, Integer userId) {
        if(post.getCaption() ==null || post.getCaption().trim().isEmpty()){
            throw new  IllegalArgumentException(" Post cannot be empty");
        }

       if(post.getCaption().length()>500){
           throw new IllegalArgumentException("Post caption cannot exceed more than 500 character");
       }
        // Set creation and update timestamps using Date
        Date now = new Date();
        post.setCreatedDate(now);
        post.setUpdatedDate(now);

        User user = userService.getCurrentUserById(userId);
        post.setUser(user);

        if(post.getUser() == null){
            throw new IllegalArgumentException("User cannot be null");
        }
        return postRepository.save(post);
    }


    @Override
    public Post getPostById(Integer id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedDateDesc();
    }

    @Override
    public List<Post> getPostsByUserId(Integer userId) {
        User user = userService.getCurrentUserById(userId);
        if(user == null){
            new IllegalArgumentException("Experience not found: "+ userId);
        }
        return postRepository.findByUser_UserIdOrderByCreatedDateDesc(userId);
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
