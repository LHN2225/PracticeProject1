package com.example.PracticeProject1.service;

import com.example.PracticeProject1.entity.Tutorial;
import com.example.PracticeProject1.repository.TutorialRepository;
import com.example.PracticeProject1.service.impl.TutorialServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TutorialServiceTest {

    @InjectMocks
    TutorialServiceImpl tutorialService;
    @Mock
    TutorialRepository tutorialRepository;

    private List<Tutorial> tutorialList;
    private Tutorial tutorial;

    @BeforeAll
    void setUp() {
         tutorialList = new ArrayList<>(
                Arrays.asList(
                        new Tutorial(1, "Title 1", "Description 1", true),
                        new Tutorial(2, "Title 2", "Description 2", true),
                        new Tutorial(3, "Title 3", "Description 3", false),
                        new Tutorial(4, "Title 4", "Description 4", false)
                )
        );

         tutorial = new Tutorial(1, "Title 1", "Description 1", true);
    }

    @AfterAll
    void tearDown() {
        tutorialList.clear();
    }

    @Test
    void findAll() {
        Mockito.when(tutorialRepository.findAll())
                .thenReturn(tutorialList);

        assertThat(tutorialService.findAll()).isNotNull();
    }

    @Test
    void findByTitleContaining() {
        Mockito.when(tutorialRepository.findByTitleContaining(tutorial.getTitle()))
                .thenReturn(
                        tutorialList.stream()
                                .filter(x -> x.getTitle().contains(tutorial.getTitle()))
                                .collect(Collectors.toList())
                );

        assertThat(tutorialService.findByTitleContaining(tutorial.getTitle())).isNotNull();
    }

    @Test
    void findByTitleContainingWithEmptyTitle() {
        Mockito.when(tutorialRepository.findByTitleContaining(""))
                .thenReturn(
                        tutorialList.stream()
                                .filter(x -> x.getTitle().contains(""))
                                .collect(Collectors.toList())
                );

        assertThat(tutorialService.findByTitleContaining("")).isNotNull();
    }

    @Test
    void findByIdWithExistID() {
        Mockito.when(tutorialRepository.findById(1L))
                .thenReturn(Optional.of(tutorial));

        assertThat(tutorialService.findById(1L)).isNotNull();
    }

    @Test
    void findByIdWithNonExistID() {
        assertThat(tutorialService.findById(2L)).isNull();
    }

    @Test
    void deleteById() {
        tutorialService.deleteById(1L);
        verify(tutorialRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteAll() {
        tutorialService.deleteAll();
        verify(tutorialRepository, times(1)).deleteAll();
    }

    @Test
    void findByPublishedReturnNotNull() {
        Mockito.when(tutorialRepository.findByPublished(true))
                .thenReturn(
                        tutorialList.stream()
                                .filter(x -> x.getPublished() == true)
                                .collect(Collectors.toList())
                );

        assertThat(tutorialService.findByPublished(true)).isNotNull();

    }

    @Test
    void saveTutorial() {
        tutorialService.saveTutorial(tutorial);

        verify(tutorialRepository, times(1)).save(tutorial);

        ArgumentCaptor<Tutorial> tutorialArgumentCaptor = ArgumentCaptor.forClass(Tutorial.class);

        verify(tutorialRepository).save(tutorialArgumentCaptor.capture());

        Tutorial savedTutorial = tutorialArgumentCaptor.getValue();

        assertThat(savedTutorial)
                .isNotNull()
                .isEqualTo(tutorial);
    }
}