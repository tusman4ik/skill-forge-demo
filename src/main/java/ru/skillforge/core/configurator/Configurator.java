package ru.skillforge.core.configurator;

import lombok.Getter;
import ru.skillforge.core.DefaultSerializer;
import ru.skillforge.core.models.Serializer;
import ru.skillforge.core.models.Type;
import ru.skillforge.core.parameter_creators.ParameterCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


@Getter
public class Configurator {


    private Serializer serializer;
    private final Class<? extends Type> type;
    private final List<Node> roots;

    private Configurator(Class<? extends Type> type) {
        this.type = type;
        roots = new ArrayList<>();
    }

    public static Configurator of(Class<? extends Type> type){
        return new Configurator(type);
    }

    public Configurator addRoot(ParameterCreator creator){
        return addRoot(creator, nodeBuilder -> {});
    }
    public Configurator addRoot(ParameterCreator creator, Consumer<NodeBuilder> consumer){
        Node node = new Node(creator);
        roots.add(node);
        NodeBuilderImpl builder = new NodeBuilderImpl(node);
        consumer.accept(builder);
        return this;
    }

    public Configurator withSerializer(Serializer serializer) {
        this.serializer = serializer;
        return this;
    }
    public Configurator withDefaultSerializer(){
        this.serializer = new DefaultSerializer();
        return this;
    }

    private record NodeBuilderImpl(Node node) implements NodeBuilder {

        @Override
            public NodeBuilder dependsOn(ParameterCreator creator) {
                node.addDependency(new Node(creator));
                return this;
            }

            @Override
            public NodeBuilder dependsOn(ParameterCreator creator, Consumer<NodeBuilder> consumer) {
                Node currNode = new Node(creator);
                node.addDependency(currNode);
                NodeBuilderImpl builder = new NodeBuilderImpl(currNode);
                consumer.accept(builder);
                return null;
            }
        }
}
