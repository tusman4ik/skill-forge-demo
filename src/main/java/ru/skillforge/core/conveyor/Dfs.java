package ru.skillforge.core.conveyor;

import ru.skillforge.core.configurator.Node;

import java.util.Random;

public class Dfs {

    private final Random random;
    private final DependencyMap map;

    public Dfs(Random random, DependencyMap map) {
        this.random = random;
        this.map = map;
    }

    public void go(Node node) {
        for(Node dependency: node.dependencies()){
            go(dependency);
        }
        map.put(node.creator().getType(), node.creator().create(random, map));
    }
}
