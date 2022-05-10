package service;

import domain.Grade;
import domain.Homework;
import domain.Pair;
import domain.Student;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import java.lang.reflect.Type;
import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private static Service service;

    @BeforeAll
    public static void setUp() throws Exception {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "C:\\egyetem\\Lab1Project\\students.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "C:\\egyetem\\Lab1Project\\homework.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "C:\\egyetem\\Lab1Project\\grades.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);

    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 110, 938, 1000})
    public void saveStudentWithInValidGroup(int group) {
        String id = "test";
        String name = "TestName";
        service.saveStudent(id, name, group);
        assertNull(service.getStudentById(id));
        service.deleteStudent("test");
    }

    @Test
    void updateNonExistentStudent() {
        String id = "test";
        String name = "TestName";
        int group = 555;
        assertEquals(0,service.updateStudent(id,name,group));
    }


    @Test
    void findAllHomeworkReturnEmptyArrayInsteadOfNUll() {
        service.deleteHomework("1");
        service.deleteHomework("2");
        service.deleteHomework("3");
        assertNotNull(service.findAllHomework());
    }

    @Test
    void saveGradeCorrectly() {
        String id = "test";
        String name = "TestName";
        int group = 555;
        String idH = "testHomework";
        String descriptionH = "This is a test homework";
        int deadlineH = 10;
        int startlineH = 5;
        service.saveHomework(idH,descriptionH,deadlineH,startlineH);
        Pair<String,String> idG = new Pair<>(id,idH);
        Grade test = new Grade(idG,9.5,9,"VeryGood");
        service.saveGrade(id,idH,7.0,9,"VeryGood");
        Grade result = service.getGradeById(id,idH);
        assertTrue(test.getGrade() == result.getGrade() && test.getID().toString().equals(result.getID().toString())
                && test.getDeliveryWeek() == result.getDeliveryWeek() && test.getFeedback().equals( result.getFeedback()));
    }


    @Test
    void extendDeadlineByToMuch() {
        int noWeeks = 150;
        String idH = "testHomework2";
        String descriptionH = "This is a test homework2";
        int deadlineH = 7;
        int startlineH = 3;
        service.saveHomework(idH,descriptionH,deadlineH,startlineH);
        assertEquals(0,service.extendDeadline(idH,noWeeks));
    }
}
