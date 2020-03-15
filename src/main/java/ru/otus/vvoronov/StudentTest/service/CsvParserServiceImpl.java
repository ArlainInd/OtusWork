package ru.otus.vvoronov.studenttest.service;

import au.com.bytecode.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.otus.vvoronov.studenttest.config.YamlConfig;

import java.io.*;

@RequiredArgsConstructor
@Service
public class CsvParserServiceImpl implements CsvParserService{
    private static final char COMMA_DELIMITER = ';';
    private final YamlConfig yamlconfig;

    @Override
    public int cvsParseQuest() throws IOException, NullPointerException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resource resource = new ClassPathResource(yamlconfig.getCsvPath());
        File file = resource.getFile();

        int answer = 0;
        int goodAnswer = 0;
        int checkAnswer = 0;

        CSVReader csvreader = new CSVReader(new FileReader(file), COMMA_DELIMITER, '"', 1);
        String[] nextLine;
        while ((nextLine = csvreader.readNext()) != null) {
            if (nextLine != null) {
                //чтеие вопроса и вариантов ответов
                System.out.println(nextLine[0]);
                System.out.println("1.) " + nextLine[1]);
                System.out.println("2.) " + nextLine[2]);
                System.out.println("3.) " + nextLine[3]);
                System.out.println("4.) " + nextLine[4]);
                goodAnswer = Integer.parseInt(nextLine[5]);
                //ввод ответа пользователя
                answer = Integer.parseInt(reader.readLine());
                //сравнение с правильным
                if (answer == goodAnswer) {
                    checkAnswer++;
                }
            }
        }

        return checkAnswer;
    }
}
