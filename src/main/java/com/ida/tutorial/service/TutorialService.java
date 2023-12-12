package com.ida.tutorial.service;

import com.ida.tutorial.dao.TutorialDao;
import com.ida.tutorial.model.Tutorial;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TutorialService {

  private final TutorialDao tutorialDao;

  public TutorialService(TutorialDao tutorialDao) {
    this.tutorialDao = tutorialDao;
  }

  static List<Tutorial> tutorials = new ArrayList<>();
  static long id = 0;

  public List<Tutorial> findAll() {
    return tutorialDao.findAll();
  }

  public List<Tutorial> findByTitleContaining(String title) {
    return tutorialDao.findByTitleContaining(title);
  }

  public Tutorial findById(long id) {
    return tutorialDao.findById(id).get();
  }

  public Tutorial save(Tutorial tutorial) {
    return tutorialDao.save(tutorial);
  }

  public void deleteById(long id) {
    tutorialDao.deleteById(id);
  }

  public void deleteAll() {
    tutorialDao.deleteAll();
  }

  public List<Tutorial> findByPublished(boolean isPublished) {
    return tutorials.stream().filter(tutorial -> isPublished == tutorial.isPublished()).toList();
  }
}
