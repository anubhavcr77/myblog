package com.myblog.Service.Impl;

import com.myblog.Service.CommentService;
import com.myblog.entity.Comment;
import com.myblog.entity.Post;
import com.myblog.exception.ResourceNotFound;
import com.myblog.payload.CommentDto;
import com.myblog.repository.CommentRepository;
import com.myblog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
private CommentRepository commentRepo;
private PostRepository postRepo;

    public CommentServiceImpl(CommentRepository commentRepo,PostRepository postRepo) {
        this.commentRepo = commentRepo;
        this.postRepo=postRepo;
    }

    @Override
    public CommentDto saveComment(CommentDto commentDto, long postId) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFound("post not found with id:" + postId));
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        comment.setPost(post);

        Comment saveComment = commentRepo.save(comment);

        CommentDto commentdto = new CommentDto();
        commentdto.setId(saveComment.getId());
        commentdto.setEmail(saveComment.getEmail());
        commentdto.setBody(saveComment.getBody());
        commentdto.setName(saveComment.getName());

        return commentdto;
    }

    @Override
    public void deleteCommentById(int id) {

        commentRepo.deleteById((long) id);
    }

    @Override
    public CommentDto updateCommentById(CommentDto commentDto, int id) {
        Comment comment = commentRepo.findById((long) id).orElseThrow(
                () -> new ResourceNotFound("Comment not found with id:" + id)
        );
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment saveComment = commentRepo.save(comment);

        CommentDto dto = new CommentDto();
        dto.setId(saveComment.getId());
        dto.setName(saveComment.getName());
        dto.setEmail(saveComment.getEmail());
        dto.setBody(saveComment.getBody());

        return dto;
    }
    @Override
    public CommentDto getCommentById(int id) {
        Comment comment = commentRepo.findById((long) id).orElseThrow(
                () -> new ResourceNotFound("Comment not found with id:" + id)
        );
        return mapToDto(comment);
    }
    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepo.findAll();
        List<CommentDto> comments1 = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return comments1;
    }
    CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }
}
