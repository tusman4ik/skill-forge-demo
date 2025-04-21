package ru.skillforge.core.models;

public record Task(
        Long id,
        Long userId,
        String problem,
        String answer,
        Long seed
) {
}
