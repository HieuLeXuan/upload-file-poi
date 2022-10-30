package com.hieulexuan.uploadexcelfile.project.service;

import com.hieulexuan.uploadexcelfile.project.model.Project;
import com.hieulexuan.uploadexcelfile.project.repository.ProjectRepository;
import com.hieulexuan.uploadexcelfile.user.model.User;
import com.hieulexuan.uploadexcelfile.user.repository.UserRepository;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

public interface ProjectService {
    List<Project> importMulFile(MultipartFile multipartFile) throws IOException;
}

@Service
class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Project> importMulFile(MultipartFile multipartFile) throws IOException {
        List<Project> listResults = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        for (int r = 1; r <= rows; r++) {
            XSSFRow row = sheet.getRow(r);

            Optional<User> existsUser = userRepository.findById(row.getCell(0).getStringCellValue());

            User user;
            if (existsUser.isEmpty()) {
                // create user
                User newUser = User.builder()
                        .id(row.getCell(0).getStringCellValue())
                        .firstName(row.getCell(1).getStringCellValue())
                        .lastName(row.getCell(2).getStringCellValue())
                        .build();
                userRepository.save(newUser);
                User savedUser = userRepository.findById(row.getCell(0).getStringCellValue()).get();
                System.out.println(savedUser.getProjects());
            }

            // create project
//            Project project = Project.builder()
//                    .name(row.getCell(3).getStringCellValue())
//                    .users(users)
//                    .build();

            // create task
//            Task task = Task.builder()
//                    .name(row.getCell(4).getStringCellValue())
//                    .description(row.getCell(5).getStringCellValue())
//                    .project(project)
//                    .build();
//            task.saveUser(user);

//            listResults.add(projectRepository.save(project));
        }
        return listResults;
    }

}
