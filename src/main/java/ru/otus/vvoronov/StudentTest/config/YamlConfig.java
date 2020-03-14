package ru.otus.vvoronov.StudentTest.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "application")
public class YamlConfig {
    private String welcometext;
    private int needAnswer;
    private String csvPath;
    private String locale;

}
