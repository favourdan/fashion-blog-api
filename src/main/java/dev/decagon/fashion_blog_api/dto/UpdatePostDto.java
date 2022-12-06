package dev.decagon.fashion_blog_api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class UpdatePostDto {

    private String title;

    private String description;

    @NotNull
    private Long userId;
}
