package com.myblog.Service;

import com.myblog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    public CommentDto saveComment(CommentDto commentDto, long postId);

    void deleteCommentById(int id);

    CommentDto updateCommentById(CommentDto commentDto, int id);

    CommentDto getCommentById(int id);

   List<CommentDto> getAllComments();
}
