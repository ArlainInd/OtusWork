package ru.otus.vvoronov.studenttest.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "application")
public class YamlConfig {
    private String welcometext;
    private int needAnswer;
    private String csvPath;
    private String locale;

}
