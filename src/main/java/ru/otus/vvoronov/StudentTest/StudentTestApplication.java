package ru.otus.vvoronov.studenttest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.vvoronov.studenttest.domain.Student;
import ru.otus.vvoronov.studenttest.service.ReadInfoService;
import ru.otus.vvoronov.studenttest.service.StudyTestingService;

@SpringBootApplication
public class StudentTestApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = SpringApplication.run(StudentTestApplication.class, args);
		StudyTestingService service = ctx.getBean(StudyTestingService.class);
		ReadInfoService readInfoService = ctx.getBean(ReadInfoService.class);
		Student student = service.startTesting(readInfoService);
		System.out.println(student.toString());
	}

}
