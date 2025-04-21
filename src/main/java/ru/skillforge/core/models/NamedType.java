package ru.skillforge.core.models;

public record NamedType(Type type, String name) {
    public NamedType(Type type) {
        this(type, null);
    }
    public String toString() {
        return name == null? type.name(): type.name() + "." + name;
    }
}
