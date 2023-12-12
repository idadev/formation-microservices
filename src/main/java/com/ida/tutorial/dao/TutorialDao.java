package com.ida.tutorial.dao;

import com.ida.tutorial.model.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TutorialDao extends JpaRepository<Tutorial, Long> {

    List<Tutorial> findByTitleContaining(String title);
}
