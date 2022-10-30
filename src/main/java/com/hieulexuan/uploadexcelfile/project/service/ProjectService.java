package com.hieulexuan.uploadexcelfile.project.service;

import com.hieulexuan.uploadexcelfile.project.model.Project;
import com.hieulexuan.uploadexcelfile.project.repository.ProjectRepository;
import com.hieulexuan.uploadexcelfile.task.model.Task;
import com.hieulexuan.uploadexcelfile.user.model.User;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ProjectService {
    List<Project> importMulFile(MultipartFile multipartFile) throws IOException;
}

@Service
class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> importMulFile(MultipartFile multipartFile) throws IOException {
        List<Project> listResults = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        for (int r = 1; r <= rows; r++) {
            XSSFRow row = sheet.getRow(r);

            // create user
            User user = User.builder()
                    .id(row.getCell(0).getStringCellValue())
                    .firstName(row.getCell(1).getStringCellValue())
                    .lastName(row.getCell(2).getStringCellValue())
                    .build();

            // create project
            Project project = Project.builder()
                    .name(row.getCell(3).getStringCellValue())
                    .build();
            System.out.println(project.toString());
            project.saveUser(user);

            // create task
            Task task = Task.builder()
                    .name(row.getCell(4).getStringCellValue())
                    .description(row.getCell(5).getStringCellValue())
                    .project(project)
                    .build();

            task.saveUser(user);

            listResults.add(projectRepository.save(project));
        }
        return listResults;
    }

}
