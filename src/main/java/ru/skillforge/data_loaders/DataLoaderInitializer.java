package ru.skillforge.data_loaders;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

@Slf4j
public class DataLoaderInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @SneakyThrows
    @Override
    public void initialize(ConfigurableApplicationContext context) {
        String pathToParameters = context.getEnvironment().getProperty("path_to_parameters");
        String pathToTemplates = context.getEnvironment().getProperty("path_to_templates");

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        assert pathToParameters != null;
        assert pathToTemplates != null;

        Resource[] parameters = resolver.getResources(pathToParameters);
        Resource[] templates = resolver.getResources(pathToTemplates);

        ParameterLoader.load(parameters, context);
        TemplatesLoader.load(templates, context);
        log.info("Data loaded");
    }

}
