package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MockServiceTest {

    @Mock
    private static StudentXMLRepository fileRepository1;

    @Mock
    private static HomeworkXMLRepository fileRepository2;

    @Mock
    private static GradeXMLRepository fileRepository3;

    public static Service service;

    public static Student insertedStudent = new Student("1","Janos",533);
    public static List<Homework> homeworks = new ArrayList<>();
    public static Homework findHomework = new Homework("1","Hello",9,6);

    @BeforeEach
    void setUp() {
        fileRepository1 = mock(StudentXMLRepository.class);
        fileRepository2 = mock(HomeworkXMLRepository.class);
        fileRepository3 = mock(GradeXMLRepository.class);

        Mockito.doReturn(insertedStudent).when(fileRepository1).findOne("1");
        Mockito.when(fileRepository1.findOne(anyString())).thenReturn(insertedStudent);
        homeworks.add(findHomework);
        Mockito.doReturn(homeworks).when(fileRepository2).findAll();

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @Test
    void saveStudent() {
        assertEquals(insertedStudent.getName(),service.getStudentById("1").getName());
    }

    @Test
    void findStudentById(){
        assertEquals("1",service.getStudentById("1").getID());
        verify(fileRepository1).findOne("1");
    }

    @Test
    void findHomework(){
        assertNotNull(service.findAllHomework());
        assertNotNull(service.findAllHomework());
        assertNotNull(service.findAllHomework());

        verify(fileRepository2,times(3)).findAll();
    }
}