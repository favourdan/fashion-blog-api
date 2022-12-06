package dev.decagon.fashion_blog_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCommentDto {
    @NotBlank
    private String title;

    @NotBlank
    private String message;

    @NotNull
    private Long userId;
}
