package org.example;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * Controller class for rendering FreeMarker templates.
 */
public class WebController {

    /**
     * Renders a FreeMarker template with the provided model.
     *
     * @param templatePath The path to the FreeMarker template.
     * @param model        The model containing data to be rendered in the template.
     * @return A string representation of the rendered template.
     */
    public static String renderTemplate(String templatePath, Map<String, Object> model) {
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
            configuration.setClassForTemplateLoading(WebController.class, "/");
            Template template = configuration.getTemplate(templatePath);
            StringWriter writer = new StringWriter();
            template.process(model, writer);
            return writer.toString();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            return "Error rendering template";
        }
    }
}