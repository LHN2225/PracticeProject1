package com.example.PracticeProject1.controller;

import com.example.PracticeProject1.dto.TutorialDto;
import com.example.PracticeProject1.entity.Tutorial;
import com.example.PracticeProject1.service.TutorialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TutorialController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TutorialControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TutorialService tutorialService;

    private List<Tutorial> tutorialList;
    private Tutorial tutorial;
    @BeforeAll
    void setUp() {
        tutorialList = new ArrayList<>(
                Arrays.asList(
                        new Tutorial(1, "Title 1", "Description 1", true),
                        new Tutorial(2, "Title 2", "Description 2", true)
                )
        );

        tutorial = new Tutorial(1, "Title 1", "Description 1", true);
    }

    @AfterAll
    void tearDown() {
        tutorialList.clear();
    }

    @Test
    void getAllTutorialsWithoutTitle() throws Exception {
        Mockito.when(tutorialService.findAll())
                .thenReturn(tutorialList);

        mockMvc.perform(get("/api/tutorials"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllTutorialsWithoutTitleWithEmptyResult() throws Exception {
        Mockito.when(tutorialService.findAll())
                .thenReturn(new ArrayList<Tutorial>());

        mockMvc.perform(get("/api/tutorials"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllTutorialsWithTitle() throws Exception {
        String title = "title";
        Mockito.when(tutorialService.findByTitleContaining(title))
                .thenReturn(tutorialList);

        mockMvc.perform(get("/api/tutorials").param("title", title))
                .andExpect(status().isOk());
    }

    @Test
    void getAllTutorialsWithTitleWithEmptyResult() throws Exception {
        String title = "title";
        Mockito.when(tutorialService.findByTitleContaining(title))
                .thenReturn(new ArrayList<Tutorial>());

        mockMvc.perform(get("/api/tutorials").param("title", title))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllTutorialsWithoutTitleThrowException() throws Exception {
        Mockito.when(tutorialService.findAll())
                .thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/tutorials"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void getAllTutorialsWithTitleThrowException() throws Exception {
        String title = "title";
        Mockito.when(tutorialService.findByTitleContaining(title))
                .thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/tutorials").param("title", title))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void getTutorialById() throws Exception {
        Mockito.when(tutorialService.findById(1L))
                .thenReturn(tutorial);

        mockMvc.perform(get("/api/tutorials/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getTutorialByIdWithTutorialNotfound() throws Exception {
        Mockito.when(tutorialService.findById(1L))
                .thenReturn(null);

        mockMvc.perform(get("/api/tutorials/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTutorialByIdThrowException() throws Exception {
        Mockito.when(tutorialService.findById(1L))
                .thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/tutorials/1"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void createTutorial() throws Exception {
        Mockito.when(tutorialService.saveTutorial(new Tutorial(0, tutorial.getTitle(), tutorial.getDescription(), tutorial.getPublished())))
                .thenReturn(tutorial);

        mockMvc.perform(post("/api/tutorials")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new TutorialDto(tutorial.getTitle(), tutorial.getDescription(), tutorial.getPublished()))))
                .andExpect(status().isCreated());
    }

    @Test
    void updateTutorial() throws Exception {
        Mockito.when(tutorialService.findById(1L))
                .thenReturn(tutorial);

        mockMvc.perform(put("/api/tutorials/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new TutorialDto(tutorial.getTitle(), tutorial.getDescription(), tutorial.getPublished()))))
                .andExpect(status().isCreated());
    }

    @Test
    void updateTutorialNotFound() throws Exception {
        Mockito.when(tutorialService.findById(1L))
                .thenReturn(null);

        mockMvc.perform(put("/api/tutorials/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new TutorialDto(tutorial.getTitle(), tutorial.getDescription(), tutorial.getPublished()))))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTutorial() throws Exception {
        mockMvc.perform(delete("/api/tutorials/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(tutorialService, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void deleteAllTutorials() throws Exception {
        mockMvc.perform(delete("/api/tutorials"))
                .andExpect(status().isNoContent());

        Mockito.verify(tutorialService, Mockito.times(1)).deleteAll();
    }

    @Test
    void findByPublished() throws Exception {
        Mockito.when(tutorialService.findByPublished(true))
                .thenReturn(tutorialList);

        mockMvc.perform(get("/api/tutorials/published"))
                .andExpect(status().isOk());
    }

    @Test
    void findByPublishedReturnEmptyList() throws Exception {
        Mockito.when(tutorialService.findByPublished(true))
                .thenReturn(new ArrayList<Tutorial>());

        mockMvc.perform(get("/api/tutorials/published"))
                .andExpect(status().isNoContent());
    }
}