package com.example.PracticeProject1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data @Getter @Setter
public class TutorialDto {
    @JsonProperty("title")
    @NotNull
    @Size(min = 2, max = 50)
    private String title;

    @JsonProperty("description")
    @NotNull
    @Size(min = 2, max = 500)
    private String description;

    @JsonProperty("published")
    @NotNull
    private Boolean published;
}
