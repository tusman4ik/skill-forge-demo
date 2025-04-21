package ru.skillforge.core.parameter_creators;


import ru.skillforge.core.conveyor.DependencyMap;
import ru.skillforge.core.models.NamedType;
import ru.skillforge.core.models.Parameter;

import java.util.Random;

public abstract class ParameterCreator {

    public abstract NamedType getType();
    public abstract Parameter create(Random random, DependencyMap map);
}
