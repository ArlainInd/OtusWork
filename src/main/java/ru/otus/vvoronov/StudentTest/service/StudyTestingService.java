package ru.otus.vvoronov.StudentTest.service;

import ru.otus.vvoronov.StudentTest.domain.Student;
import java.io.IOException;

public interface StudyTestingService {
    Student startTesting() throws IOException, NullPointerException;
}
