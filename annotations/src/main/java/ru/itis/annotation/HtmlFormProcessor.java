package ru.itis.annotation;


import ru.itis.annotation.annotation.HtmlForm;
import ru.itis.annotation.annotation.HtmlInput;
import ru.itis.annotation.html.Form;
import ru.itis.annotation.html.HtmlFormBuilder;
import ru.itis.annotation.html.Input;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;


@SupportedAnnotationTypes(value = {"ru.itis.annotation.annotation.HtmlForm"})
public class HtmlFormProcessor extends AbstractProcessor {

    private HtmlFormBuilder htmlFormBuilder;

    public HtmlFormProcessor() {
        htmlFormBuilder = new HtmlFormBuilder();
    }

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(ru.itis.annotation.annotation.HtmlForm.class);
        annotatedElements.forEach(element -> {
            Form form = new Form(element.getAnnotation(HtmlForm.class).action(), element.getAnnotation(HtmlForm.class).method());
            element.getEnclosedElements()
                    .stream()
                    .filter(field -> field.getAnnotation(HtmlInput.class) != null)
                    .forEach(field -> form.getInputs().add(new Input(field.getAnnotation(HtmlInput.class).type(),
                            field.getAnnotation(HtmlInput.class).name(),
                            field.getAnnotation(HtmlInput.class).placeholder())));
            String path = HtmlFormProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(1)
                    + element.getSimpleName()
                    + ".html";
            htmlFormBuilder.buildHtmlForm(path, form);
        });
        return true;
    }
}
