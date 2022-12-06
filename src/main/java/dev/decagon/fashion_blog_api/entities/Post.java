package dev.decagon.fashion_blog_api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "Post")
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(
            mappedBy = "post",
            orphanRemoval = true,
            cascade = CascadeType.ALL

    )
    @ToString.Exclude
    @JsonIgnore
    private final List<Comment> comments = new ArrayList<>();

    @PrePersist
    public void setCreatedAt(){
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    public void setUpdatedAt(){
        updatedAt = LocalDateTime.now();
    }

    public Post(String title, String description){
        this.title = title;
        this.description = description;
    }

}
