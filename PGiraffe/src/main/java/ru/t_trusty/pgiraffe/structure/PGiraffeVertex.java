
package ru.t_trusty.pgiraffe.structure;

import java.util.Iterator;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import org.apache.tinkerpop.gremlin.structure.VertexProperty.Cardinality;


/**
 * @author Alik Khilazhev (https://github.com/alikhil)
 */
public final class PGiraffeVertex extends PGiraffeElement implements Vertex {

    private final PGiraffeGraph graph;

    public PGiraffeVertex(final Object id, final String label, final PGiraffeGraph graph) {
        super(id, label);
        this.graph = graph;
    }

    @Override
    public Object id() {
        return null;
    }

    public String label() {
        return null;
    }

    @Override
    public Graph graph() {

        return this.graph;
    }

    public void remove() {

    }

    @Override
    public Edge addEdge(String label, Vertex inVertex, Object... keyValues) {

        if (inVertex == null) throw Graph.Exceptions.argumentCanNotBeNull("inVertex");
        if (this.removed) throw elementAlreadyRemoved(Vertex.class, this.id);
        return PGiraffeHelper.addEdge(this.graph, this, (PGiraffeVertex) inVertex, label, keyValues);
    }

    @Override
    public <V> VertexProperty<V> property(Cardinality cardinality, String key, V value, Object... keyValues) {
        return null;
    }


    @Override
    public Iterator<Edge> edges(Direction direction, String... edgeLabels) {
        return null;
    }

    @Override
    public Iterator<Vertex> vertices(Direction direction, String... edgeLabels) {
        return null;
    }

    @Override
    public <V> Iterator<VertexProperty<V>> properties(String... propertyKeys) {
        return null;
    }

}