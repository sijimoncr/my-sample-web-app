package com.example.webapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TemplateHelper {
    
    public static String loadTemplate(String templateName) throws IOException {
        Path templatePath = Path.of("src/main/webapp/WEB-INF/templates/" + templateName + ".html");
        return Files.readString(templatePath);
    }
    
    public static String replaceTokens(String template, String... replacements) {
        String result = template;
        for (int i = 0; i < replacements.length; i += 2) {
            String token = "{{" + replacements[i] + "}}";
            String value = replacements[i + 1];
            result = result.replace(token, value);
        }
        return result;
    }
}