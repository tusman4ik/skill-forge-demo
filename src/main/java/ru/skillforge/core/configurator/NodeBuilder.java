package ru.skillforge.core.configurator;

import ru.skillforge.core.parameter_creators.ParameterCreator;

import java.util.function.Consumer;

public interface NodeBuilder  {
    NodeBuilder dependsOn(ParameterCreator creator);
    NodeBuilder dependsOn(ParameterCreator creator, Consumer<NodeBuilder> consumer);
}
