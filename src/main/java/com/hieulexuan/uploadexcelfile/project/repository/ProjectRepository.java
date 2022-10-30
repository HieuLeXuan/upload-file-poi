package com.hieulexuan.uploadexcelfile.project.repository;

import com.hieulexuan.uploadexcelfile.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByName(String stringCellValue);
}
