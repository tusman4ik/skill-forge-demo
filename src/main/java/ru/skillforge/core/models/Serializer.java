package ru.skillforge.core.models;

import ru.skillforge.core.conveyor.DependencyMap;

public interface Serializer {
    String serialize(String template, DependencyMap map);
}
