package ru.otus.vvoronov.studenttest.service;

import ru.otus.vvoronov.studenttest.domain.Student;

public interface StudyTestingService {
    Student startTesting(ReadInfoService readInfoService) throws Exception;
}
