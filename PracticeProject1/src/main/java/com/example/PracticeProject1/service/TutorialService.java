package com.example.PracticeProject1.service;

import com.example.PracticeProject1.entity.Tutorial;

import java.util.List;

public interface TutorialService {
    List<Tutorial> findAll();
    List<Tutorial> findByTitleContaining(String title);
    Tutorial findById(long id);
    void deleteById(long id);
    void deleteAll();
    List<Tutorial> findByPublished(boolean published);
    Tutorial saveTutorial(Tutorial tutorial);

}
