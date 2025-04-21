package ru.skillforge.core.conveyor;

import ru.skillforge.core.Resource;
import ru.skillforge.core.configurator.Configurator;
import ru.skillforge.core.configurator.Node;
import ru.skillforge.core.models.Task;
import ru.skillforge.core.models.Type;

import java.util.Random;

public class Conveyor {

    private final Configurator configurator;
    private final static Random seedInit = new Random();
    private final Resource resource;

    public Conveyor(Configurator configurator, Resource resource) {
        this.configurator = configurator;
        this.resource = resource;
    }

    public Class<? extends Type> getType(){
        return configurator.getType();
    }

    public Task create(Long seed){
        Random random = new Random(seed);

        DependencyMap map = new DependencyMap();
        Dfs dfs = new Dfs(random, map);

        for(Node node: configurator.getRoots()){
            dfs.go(node);
        }
//        String problem = configurator.getSerializer().serialize();
        return null;
    }

    public Task create(){
        return create(seedInit.nextLong());
    }


}
