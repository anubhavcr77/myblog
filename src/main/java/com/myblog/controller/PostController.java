package com.myblog.controller;

import com.myblog.Service.PostService;
import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }
  @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto,
                                          BindingResult result
                                          ){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto Dto = postService.createPost(postDto);

        return new ResponseEntity<>(Dto, HttpStatus.CREATED);

//http://Localhost:8080/api/posts

    }
    //http://Localhost:8080/api/posts/{postId}
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{postId}")
    public ResponseEntity<String> deletePost(@PathVariable int postId){
        postService.deleteById(postId);
        return new ResponseEntity<>("Post is deleted with id", HttpStatus.OK);

    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("{postId}")
    public ResponseEntity<PostDto> getpostBypostId(@PathVariable int postId){
        PostDto dto = postService.getpostBypostId(postId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    //http://localhost:8080/api/posts?pageNo=0&pageSize=3&sortBy=description&sortDir=asc
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam (value = "sortDir", defaultValue = "asc", required=false) String sortDir){
        PostResponse postResponse = postService.getAllPosts(pageNo,pageSize, sortBy,sortDir );
       return postResponse;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{postId}")
    public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto,@PathVariable int postId){
        PostDto postDto1 = postService.updatePostById(postDto,postId);
        return new ResponseEntity<>(postDto1, HttpStatus.OK);
    }
}
