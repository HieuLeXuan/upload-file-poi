package com.hieulexuan.uploadexcelfile.project.resource;

import com.hieulexuan.uploadexcelfile.project.model.Project;
import com.hieulexuan.uploadexcelfile.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectResource {

    @Autowired
    private ProjectService projectService;

    @Transactional
    @PostMapping(value = "/import-files", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> uploadFile(@RequestParam MultipartFile multipartFile) {
        try {
            List<Project> listResults = projectService.importMulFile(multipartFile);
            return new ResponseEntity<>(listResults, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
