package ru.skillforge.data_loaders;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ParameterLoader {
    private ParameterLoader() {
    }

    public static void load(Resource[] parameters, ConfigurableApplicationContext context) {
        YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
        CompositePropertySource source = new CompositePropertySource("parameters");
        for (Resource parameter : parameters) {
            source.addPropertySource(loadResource(parameter, loader));
        }
        context.getEnvironment().getPropertySources().addFirst(source);
        log.info("Parameters loaded");

    }

    @SneakyThrows
    private static PropertySource<?> loadResource(Resource resource, YamlPropertySourceLoader loader) {

        var source = new CompositePropertySource(name(resource));
        loader.load(name(resource), resource)
                .stream()
                .map(ParameterLoader::transform)
                .forEach(source::addPropertySource);
        return source;

    }

    private static PropertySource<?> transform(PropertySource<?> propertySource) {
        Map<String, Object> answer = new HashMap<>();

        if (propertySource.getSource() instanceof Map<?, ?> map) {
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                answer.put(propertySource.getName() + "." + entry.getKey(), entry.getValue().toString());
            }
        } else {
            throw new IllegalArgumentException("Unsupported property source type: " + propertySource.getClass());
        }
        return new MapPropertySource(propertySource.getName(), answer);
    }


    private static String name(Resource resource) {
        String filename = resource.getFilename();

        assert filename != null;
        return filename.substring(0, filename.lastIndexOf('.'));

    }

}
