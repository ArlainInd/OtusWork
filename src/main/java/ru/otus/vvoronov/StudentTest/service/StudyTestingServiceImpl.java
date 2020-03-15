package ru.otus.vvoronov.studenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.vvoronov.studenttest.config.YamlConfig;
import ru.otus.vvoronov.studenttest.domain.Student;
import java.io.*;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class StudyTestingServiceImpl implements StudyTestingService{

    private final YamlConfig yamlconfig;
    private final CsvParserService csvParser;

    @Override
    public Student startTesting() throws IOException, NullPointerException {
        Student student = new Student();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Locale.setDefault(new Locale(yamlconfig.getLocale()));

        System.out.println("Введите вашу фамилию:");
        String lastName = reader.readLine();
        student.setLastName(lastName);
        System.out.println("Введите ваше имя:");
        String firstName = reader.readLine();
        student.setFirstName(firstName);

        student.setCntOk(csvParser.cvsParseQuest());
        if (student.getCntOk() >= yamlconfig.getNeedAnswer()) {
            student.setIsComplateTest(Boolean.TRUE);
        }
        return student;
    }
}
