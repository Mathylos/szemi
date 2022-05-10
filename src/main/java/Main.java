

import console.*;
import domain.*;
import repository.*;
import service.*;
import validation.*;

public class Main {
    public static void main(String[] args) {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "C:\\egyetem\\Lab1Project\\students.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "C:\\egyetem\\Lab1Project\\homework.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "C:\\egyetem\\Lab1Project\\grades.xml");

        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        UI console = new UI(service);
        console.run();

    }
}
