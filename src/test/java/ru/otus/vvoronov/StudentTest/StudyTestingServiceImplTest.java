package ru.otus.vvoronov.studenttest;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.vvoronov.studenttest.domain.Student;
import ru.otus.vvoronov.studenttest.service.ReadInfoService;
import ru.otus.vvoronov.studenttest.service.ReadInfoServiceImpl;
import ru.otus.vvoronov.studenttest.service.StudyTestingService;
import ru.otus.vvoronov.studenttest.service.StudyTestingServiceImpl;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@DisplayName("Методы сервиса опроса студентов ")
@SpringBootTest(classes = StudyTestingServiceImpl.class)
@ContextConfiguration(classes = StudentTestApplication.class)
class StudyTestingServiceImplTest {

	@Autowired
	private StudyTestingService service;

	@Mock
	private ReadInfoServiceImpl readInfoService;

	@Test
	public void checkStudentTest() throws Exception {
		Student studentExample = new Student();
		studentExample.setFirstName("Тест");
		studentExample.setLastName("Тест");
		studentExample.setCntOk(3);
		studentExample.setTestCompleted(Boolean.FALSE);
		//как протетировать метод если он ожидает ввода строки а тесты этого не позволяют и зависают?
		when(readInfoService.readName()).thenReturn("Тест");
		when(readInfoService.readAnswer()).thenReturn(1);
		Student student = service.startTesting(readInfoService);
		System.out.println(student.toString());
		Assert.assertEquals(studentExample.toString(), student.toString());
	}

}
