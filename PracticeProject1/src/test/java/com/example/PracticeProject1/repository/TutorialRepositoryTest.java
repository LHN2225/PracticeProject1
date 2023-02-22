package com.example.PracticeProject1.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TutorialRepositoryTest {

    @Autowired
    TutorialRepository tutorialRepository;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findByPublishedNotNull() {
        assertThat(tutorialRepository.findByPublished(true)).isNotNull();

    }

    @Test
    void findByNotPublishedNotNull() {
        assertThat(tutorialRepository.findByPublished(false)).isNotNull();
    }

    @Test
    void findByTitleContainingWithValue() {
        assertThat(tutorialRepository.findByTitleContaining("title")).isNotNull();
    }
    @Test
    void findByTitleContainingWithEmptyValue() {
        assertThat(tutorialRepository.findByTitleContaining("")).isNotNull();
    }
}