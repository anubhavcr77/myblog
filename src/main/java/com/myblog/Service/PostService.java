package com.myblog.Service;

import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;

import java.util.List;

public interface PostService {

    public PostDto createPost(PostDto postDto);

    void deleteById(int postId);

    PostDto getpostBypostId(int postId);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto updatePostById(PostDto postDto, int postId);
}
