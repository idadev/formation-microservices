package com.ida.tutorial.controller;

import com.ida.tutorial.model.Tutorial;
import com.ida.tutorial.service.TutorialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "tutoriels", description = "Tutorial management APIs")
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TutorialController {
  @Autowired
  TutorialService tutorialService;

  @Operation(summary = "Créer un nouveau tutoriel")
  @ApiResponses({
      @ApiResponse(responseCode = "201", content = {
          @Content(schema = @Schema(implementation = Tutorial.class), mediaType = "application/json") }),
      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
  @PostMapping("/tutorials")
  public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
    try {
      Tutorial _tutorial = tutorialService
          .save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished()));
      return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Lister tous les tutoriels")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = Tutorial.class), mediaType = "application/json") }),
      @ApiResponse(responseCode = "204", description = "There are no Tutorials", content = {
          @Content(schema = @Schema()) }),
      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
  @GetMapping("/tutorials")
  public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
    try {
      List<Tutorial> tutorials = new ArrayList<>();

      if (title == null)
        tutorialService.findAll().forEach(tutorials::add);
      else
        tutorialService.findByTitleContaining(title).forEach(tutorials::add);

      if (tutorials.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(tutorials, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(
      summary = "Retourne le tutoriel par id")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Tutorial.class), mediaType = "application/json") }),
      @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
  @GetMapping("/tutorials/{id}")
  public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
    Tutorial tutorial = tutorialService.findById(id);

    if (tutorial != null) {
      return new ResponseEntity<>(tutorial, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Operation(summary = "Met à jour un toturiel avec son id")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = Tutorial.class), mediaType = "application/json") }),
      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }),
      @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
  })
  @PutMapping("/tutorials/{id}")
  public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
    Tutorial tutorialToModify = tutorialService.findById(id);

    if (tutorialToModify != null) {
      tutorialToModify.setTitle(tutorial.getTitle());
      tutorialToModify.setDescription(tutorial.getDescription());
      tutorialToModify.setPublished(tutorial.isPublished());
      return new ResponseEntity<>(tutorialService.save(tutorialToModify), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Operation(summary = "Supprime un tutoriel avec son Id")
  @ApiResponses({ @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
  @DeleteMapping("/tutorials/{id}")
  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
    try {
      tutorialService.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Supprime tous les tutoriels")
  @ApiResponses({ @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
  @DeleteMapping("/tutorials")
  public ResponseEntity<HttpStatus> deleteAllTutorials() {
    try {
      tutorialService.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }
}
