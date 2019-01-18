
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

    public Object id() {
        return null;
    }

    public String label() {
        return null;
    }

    public Graph graph() {
        return null;
    }

    public void remove() {

    }

    public Edge addEdge(String label, Vertex inVertex, Object... keyValues) {
        return null;
    }

    public <V> VertexProperty<V> property(Cardinality cardinality, String key, V value, Object... keyValues) {
        return null;
    }

    public Iterator<Edge> edges(Direction direction, String... edgeLabels) {
        return null;
    }

	public Iterator<Vertex> vertices(Direction direction, String... edgeLabels) {
		return null;
	}

	public <V> Iterator<VertexProperty<V>> properties(String... propertyKeys) {
		return null;
	}

}