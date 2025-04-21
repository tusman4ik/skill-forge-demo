package ru.skillforge.core.configurator;

import ru.skillforge.core.parameter_creators.ParameterCreator;

import java.util.ArrayList;
import java.util.List;


public record Node(ParameterCreator creator, List<Node> dependencies) {

    public Node(ParameterCreator creator) {
        this(creator, new ArrayList<>());
    }

    void addDependency(Node node) {
        dependencies.add(node);
    }
}
