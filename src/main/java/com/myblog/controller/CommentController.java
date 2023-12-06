package com.myblog.controller;

import com.myblog.Service.CommentService;
import com.myblog.payload.CommentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    //http://localhost:8080/api/comments/{postId}
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("{postId}")
    public ResponseEntity<CommentDto> saveComment (@RequestBody CommentDto commentDto, @PathVariable long postId){
        CommentDto dto = commentService.saveComment(commentDto, postId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @DeleteMapping("{id}")
public ResponseEntity<String> deleteCommentById(@PathVariable int id){
        commentService.deleteCommentById(id);
        return  new ResponseEntity<>("Comment is deleted ", HttpStatus.OK);
}
@PutMapping("{id}")
public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable int id){
    CommentDto dto = commentService.updateCommentById(commentDto, id);
    return  new ResponseEntity<>(dto, HttpStatus.OK);
}
@GetMapping("{id}")
public ResponseEntity<CommentDto> getCommentById(@PathVariable int id){
    CommentDto commentDto1 = commentService.getCommentById(id);
    return  new ResponseEntity<>(commentDto1, HttpStatus.OK);
}
@GetMapping
    public List<CommentDto> getAllComments(){
       List<CommentDto> comments = commentService.getAllComments();

       return comments;
    }

}
