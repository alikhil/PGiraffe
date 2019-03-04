
package ru.t_trusty.pgiraffe.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import org.apache.tinkerpop.gremlin.structure.VertexProperty.Cardinality;
import org.apache.tinkerpop.gremlin.structure.util.ElementHelper;
import org.apache.tinkerpop.gremlin.util.iterator.IteratorUtils;


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
        return this.id;
    }

    @Override
    public void removeProperty(String key) {
        // todo: implement this
    }

    public String label() {
        return this.label;
    }

    @Override
    public Graph graph() {

        return this.graph;
    }

    public void remove() {
        // remove all in and out edges of current vertex
        final List<Edge> edges = new ArrayList<>();
        this.edges(Direction.BOTH).forEachRemaining(edges::add);
        edges.stream()
                .filter(edge -> !((PGiraffeEdge) edge).removed)
                .forEach(Edge::remove);

        this.graph.engine().removeVertex(this);
        this.removed = true;
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

        return this.graph.engine().getEdges(this, direction, edgeLabels);
    }

    @Override
    public Iterator<Vertex> vertices(Direction direction, String... edgeLabels) {

        return direction.equals(Direction.BOTH) ?
                IteratorUtils.concat(
                        IteratorUtils.map(this.edges(Direction.OUT, edgeLabels), Edge::inVertex),
                        IteratorUtils.map(this.edges(Direction.IN, edgeLabels), Edge::outVertex)) :
                IteratorUtils.map(this.edges(direction, edgeLabels), edge -> edge.vertices(direction.opposite()).next());
    }

    @Override
    public <V> Iterator<VertexProperty<V>> properties(String... propertyKeys) {

        if (this.removed) {
            return Collections.emptyIterator();
        }
//        if (null == this.properties) return Collections.emptyIterator();
        return this.graph.engine().getVertexProperties(this, propertyKeys);

    }

}