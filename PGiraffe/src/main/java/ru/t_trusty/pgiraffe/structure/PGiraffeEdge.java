package ru.t_trusty.pgiraffe.structure;

import java.util.Collections;
import java.util.Iterator;

import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Property;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.util.iterator.IteratorUtils;

/**
 * @author Alik Khilazhev (https://github.com/alikhil)
 */

public final class PGiraffeEdge extends PGiraffeElement implements Edge {


    private final Vertex outVertex;
    private final Vertex inVertex;

    protected PGiraffeEdge(final Object id, final Vertex outVertex, final String label, final Vertex inVertex) {
        super(id, label);
        this.inVertex = inVertex;
        this.outVertex = outVertex;

    }

    public Object id() {

        return this.id;
    }

    public String label() {

        return this.label;
    }

    public Graph graph() {
        return this.inVertex.graph();
    }

    public <V> Property<V> property(String key, V value) {
        return null;
    }

    public void remove() {

        // remove edge should do all the dirty work with
        ((PGiraffeGraph) graph()).engine().removeEdge(this);
        removed = true;
    }

    public Iterator<Vertex> vertices(Direction direction) {
        if (removed) return Collections.emptyIterator();
        switch (direction) {
            case OUT:
                return IteratorUtils.of(this.outVertex);
            case IN:
                return IteratorUtils.of(this.inVertex);
            default:
                return IteratorUtils.of(this.outVertex, this.inVertex);
        }
    }

    public <V> Iterator<Property<V>> properties(String... propertyKeys) {
        return null;
    }

}