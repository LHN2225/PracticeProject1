package com.example.PracticeProject1.controller;

import com.example.PracticeProject1.dto.TutorialDto;
import com.example.PracticeProject1.entity.Tutorial;
import com.example.PracticeProject1.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class TutorialController {
    @Autowired
    private TutorialService tutorialService;

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(name = "title", required = false) String title) {

        try {
            List<Tutorial> tutorials = new ArrayList<>();
            if (title == null)
                tutorials = tutorialService.findAll();
            else
                tutorials = tutorialService.findByTitleContaining(title);

            if (tutorials.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable(name = "id") long id) {

        try {
            Tutorial tutorial = tutorialService.findById(id);
            if (tutorial == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(tutorial, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody @Valid TutorialDto tutorialDto) {

        try {
            Tutorial tutorial = new Tutorial();
            tutorial.setTitle(tutorialDto.getTitle());
            tutorial.setDescription(tutorialDto.getDescription());
            tutorial.setPublished(tutorialDto.getPublished());

            Tutorial res_tutorial = tutorialService.saveTutorial(tutorial);
            return new ResponseEntity<>(res_tutorial, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable(name = "id") long id,
                                                   @RequestBody @Valid TutorialDto tutorialDto) {
        try {
            Tutorial tutorial = tutorialService.findById(id);
            if (tutorial == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            tutorial.setTitle(tutorialDto.getTitle());
            tutorial.setDescription(tutorialDto.getDescription());
            tutorial.setPublished(tutorialDto.getPublished());

            Tutorial res_tutorial = tutorialService.saveTutorial(tutorial);
            return new ResponseEntity<>(res_tutorial, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable(name = "id") long id) {

        try {
            tutorialService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {

        try {
            tutorialService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<Tutorial>> findByPublished() {

        try {
            List<Tutorial> tutorials = tutorialService.findByPublished(true);

            if (tutorials.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
