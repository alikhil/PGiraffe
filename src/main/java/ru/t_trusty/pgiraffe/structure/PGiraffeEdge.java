package ru.t_trusty.pgiraffe.structure;

import java.util.Iterator;

import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Property;
import org.apache.tinkerpop.gremlin.structure.Vertex;

/**
 * @author Alik Khilazhev (https://github.com/alikhil)
 */

public final class PGiraffeEdge extends PGiraffeElement implements Edge {

    
    protected PGiraffeEdge(final Object id, final Vertex outVertex, final String label, final Vertex inVertex) {
        super(id, label);

    }

    public Object id() {
        return null;
    }

    public String label() {
        return null;
    }

    public Graph graph() {
        return null;
    }

    public <V> Property<V> property(String key, V value) {
        return null;
    }

    public void remove() {

    }

    public Iterator<Vertex> vertices(Direction direction) {
        return null;
    }

    public <V> Iterator<Property<V>> properties(String... propertyKeys) {
        return null;
    }

}