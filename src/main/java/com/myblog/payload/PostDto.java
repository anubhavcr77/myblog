package com.myblog.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Setter
@Getter
public class PostDto {
    private long id;
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String Title;
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;
    @NotEmpty
    private String content;
}
