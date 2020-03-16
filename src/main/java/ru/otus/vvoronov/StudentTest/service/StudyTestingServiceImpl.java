package ru.otus.vvoronov.studenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.vvoronov.studenttest.config.YamlConfig;
import ru.otus.vvoronov.studenttest.domain.Student;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class StudyTestingServiceImpl implements StudyTestingService{

    private final YamlConfig yamlconfig;
    private final CsvParserService csvParser;
    //private final ReadInfoService readInfoService;

    @Override
    public Student startTesting(ReadInfoService readInfoService) throws Exception {
        Student student = new Student();

        Locale.setDefault(new Locale(yamlconfig.getLocale()));

        System.out.println("Введите вашу фамилию:");
        String lastName = readInfoService.readName();
        student.setLastName(lastName);
        System.out.println("Введите ваше имя:");
        String firstName = readInfoService.readName();
        student.setFirstName(firstName);

        student.setCntOk(csvParser.cvsParseQuest(readInfoService));
        if (student.getCntOk() >= yamlconfig.getNeedAnswer()) {
            student.setTestCompleted(Boolean.TRUE);
        }
        return student;
    }
}
