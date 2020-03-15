package ru.otus.vvoronov.studenttest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.vvoronov.studenttest.service.StudyTestingService;
import ru.otus.vvoronov.studenttest.service.StudyTestingServiceImpl;

import java.io.IOException;

@DisplayName("Методы сервиса приветствий должны ")
@SpringBootTest(classes = StudyTestingServiceImpl.class)
@ContextConfiguration(classes = StudentTestApplication.class)
class StudyTestingServiceImplTest {

	@Autowired
	private StudyTestingService service;

	@Test
	public void sayHelloTest() throws IOException {
		//как протетировать метод если он ожидает ввода строки а тесты этого не позволяют и зависают?
		service.startTesting();
	}

}
