package ru.skillforge.core;

import org.springframework.context.annotation.Bean;
import ru.skillforge.core.configurator.Configurator;
import ru.skillforge.core.conveyor.DependencyMap;
import ru.skillforge.core.models.Data.t_1.t_1_1;
import ru.skillforge.core.models.NamedType;
import ru.skillforge.core.models.Parameter;
import ru.skillforge.core.parameter_creators.ParameterCreator;

import java.util.List;
import java.util.Random;

public class TestConfig {

    ParameterCreator cntLeg = new ParameterCreator() {
        @Override
        public NamedType getType() {
            return new NamedType(t_1_1.CNT_LEGS);
        }

        @Override
        public Parameter create(Random random, DependencyMap map) {
            //Типа из ресурсов взяли диапазон
            int start = 1, end = 5;
            return new Parameter() {
                @Override
                public String getParameter() {
                    return String.valueOf(random.nextInt(start, end));
                }
            };
        }
    };
    ParameterCreator name = new ParameterCreator() {
        @Override
        public NamedType getType() {
            return new NamedType(t_1_1.ANIMAL, "firstAnimal");
        }

        @Override
        public Parameter create(Random random, DependencyMap map) {
            //типа доступ к ресурсам
            List<String> list = List.of("Cat", "Dog", "Fish");
            return new Parameter() {
                @Override
                public String getParameter() {
                    return list.get(random.nextInt(list.size()));
                }
            };
        }
    };

    @Bean
    private Configurator t_1() {
        return Configurator.of(t_1_1.class)
                .addRoot(name, name ->{
                    name.dependsOn(cntLeg, cntLeg ->{
//                        cntLeg.dependsOn() <-пример большой вложенности
                    });
                })
                .addRoot(name);//<- если ни от чего не зависит
    }
}
