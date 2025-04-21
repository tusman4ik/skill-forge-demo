package ru.skillforge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import ru.skillforge.data_loaders.DataLoaderInitializer;

import java.io.IOException;

@SpringBootApplication
@Slf4j
public class App {
    public static void main(String[] args) throws IOException {

        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(App.class)
                .initializers(new DataLoaderInitializer())
                .run(args);


    }
}
