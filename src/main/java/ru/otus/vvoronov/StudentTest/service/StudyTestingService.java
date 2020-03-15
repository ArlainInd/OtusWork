package ru.otus.vvoronov.studenttest.service;

import ru.otus.vvoronov.studenttest.domain.Student;
import java.io.IOException;

public interface StudyTestingService {
    Student startTesting() throws IOException, NullPointerException;
}
