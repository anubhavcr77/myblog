package com.myblog.Service.Impl;

import com.myblog.Service.PostService;
import com.myblog.entity.Post;
import com.myblog.exception.ResourceNotFound;
import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import com.myblog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper modelMapper;
    public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }
    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);

        PostDto Dto = new PostDto();
        Dto.setId(post.getId());
        Dto.setTitle(post.getTitle());
        Dto.setDescription(post.getDescription());
        Dto.setContent(post.getContent());

        return Dto;
    }
    @Override
    public void deleteById(int postId) {
        Post post = postRepository.findById((long) postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with id:" + postId)
        );
        postRepository.deleteById((long) postId);
    }
    @Override
    public PostDto getpostBypostId(int postId) {
        Post post = postRepository.findById((long) postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with id:" + postId)
        );
        return mapToDto(post);
    }
    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> all = postRepository.findAll(pageable);
        List<Post> posts = all.getContent();
        List<PostDto> dtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(dtos);
        postResponse.setPageNo(all.getNumber());
        postResponse.setPageSize(all.getSize());
        postResponse.setTotalElements((int)all.getTotalElements());
        postResponse.setTotalPages(all.getTotalPages());
        postResponse.setLast(all.isLast());
        return postResponse;
    }
    @Override
    public PostDto updatePostById(PostDto postDto, int postId) {
        Post post = postRepository.findById((long) postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with id:" + postId)
        );
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post savePost = postRepository.save(post);

        PostDto dto1 = new PostDto();
        dto1.setId(savePost.getId());
        dto1.setTitle(savePost.getTitle());
        dto1.setDescription(savePost.getDescription());
        dto1.setContent(savePost.getContent());
        return dto1;
    }
    PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);
       /* PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());*/
        return dto;
    }
}
