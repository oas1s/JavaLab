package ru.itis.annotation.html;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class HtmlFormBuilder {
    private Configuration configuration;

    public HtmlFormBuilder() {
        configuration = new Configuration(Configuration.VERSION_2_3_30);
        configuration.setClassForTemplateLoading(this.getClass(), "/");
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        configuration.setWrapUncheckedExceptions(true);
    }

    public void buildHtmlForm(String filePath, Form form) {
        try {
            Template t = configuration.getTemplate("template.ftlh");
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(file);
            HashMap<String, Object> root = new HashMap<>();
            root.put("form", form);
            t.process(root, fileWriter);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
