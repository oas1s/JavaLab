package ru.itis.servlets.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.servlets.dto.PostDto;
import ru.itis.servlets.models.Post;
import ru.itis.servlets.repositories.PostRepositoryImpl;
import ru.itis.servlets.security.details.UserDetailsImpl;

import java.util.List;

@Controller
public class PostsController {

    @Autowired
    private PostRepositoryImpl postRepository;

    @GetMapping("/myposts")
    public String getPosts(Authentication authentication, Model model){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<Post> posts = postRepository.findByUser(userDetails.getUser());
        model.addAttribute("posts",posts);
        return "posts";
    }

    @PostMapping("/addpost")
    public String addPosts(PostDto postDto, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Post post = Post.builder().name(postDto.getName()).text(postDto.getText()).user_id(userDetails.getUser()).build();
        postRepository.save(post);
        return "redirect:myposts";
    }

    @GetMapping("/addpost")
    public String addPostsPage(){
        return "addpost";
    }
}
