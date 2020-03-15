package ru.otus.vvoronov.studenttest.domain;

import lombok.Data;

@Data
public class Student {
    private String lastName;
    private String firstName;
    private Boolean isComplateTest;
    private int cntOk;

    public String toString() {
        String res = "";
        if (isComplateTest == Boolean.TRUE) {
            res = "успешно пройден";
        }
        else {
            res = "не пройден";
        }
        return "Студент(ка) " + lastName + " " + firstName + " успешно ответил на " + cntOk +
                " вопрос(а). Результат тестирования - " + res;
    }
}
