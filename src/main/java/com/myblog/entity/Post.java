package com.myblog.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="title", nullable =false)
    private String title;
    @Column(name="description", nullable =false)
    private String description;

    @Column(name="content", nullable =false)
    private String content;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();
}
