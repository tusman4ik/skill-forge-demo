package ru.skillforge.data_loaders;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class TemplatesLoader {
    private TemplatesLoader() {
    }


    public static void load(Resource[] templates, ConfigurableApplicationContext context) {
        Map<String, List<String>> prefixedMap = new HashMap<>();
        for (Resource template : templates) {
            String key = name(template);
            String prefix = key.substring(0, key.lastIndexOf('_'));
            prefixedMap.computeIfAbsent(prefix, k -> new ArrayList<>()).add(read(template));
        }
        CompositePropertySource source = new CompositePropertySource("templates");
        for (Map.Entry<String, List<String>> entry : prefixedMap.entrySet()) {
            source.addPropertySource(new MapPropertySource(entry.getKey(), Map.of(entry.getKey(), entry.getValue())));
        }
        context.getEnvironment().getPropertySources().addFirst(source);
        log.info("Templates loaded");

    }



    private static String name(Resource resource) {
        String filename = resource.getFilename();
        assert filename != null;
        return filename.substring(0, filename.lastIndexOf('.'));
    }

    @SneakyThrows
    private static String read(Resource resource) {
        try (InputStream is = resource.getInputStream()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
