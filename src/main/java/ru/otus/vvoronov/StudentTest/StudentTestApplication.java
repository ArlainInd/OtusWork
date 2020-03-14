package ru.otus.vvoronov.StudentTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.vvoronov.StudentTest.config.YamlConfig;
import ru.otus.vvoronov.StudentTest.domain.Student;
import ru.otus.vvoronov.StudentTest.service.StudyTestingService;

@SpringBootApplication
@EnableConfigurationProperties(YamlConfig.class)
@EnableAutoConfiguration
@ComponentScan
public class StudentTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentTestApplication.class, args);

		//если через контекст, то тогда не считывает application.yaml
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(StudentTestApplication.class);
		StudyTestingService service = context.getBean(StudyTestingService.class);

		//если вместо контекста вот так написать, то настройки считываются, но после конструктора валит NullPointerException
		//StudyTestingService service = null;
		Student student = null;
		try {
			student = service.startTesting();
			System.out.println(student.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
